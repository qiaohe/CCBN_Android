package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.MessageEvent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.entities.CheckInData;
import com.exhibition.entities.EventData;
import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.entities.EventData.Speaker;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.netty.client.ClientData;
import com.exhibition.netty.client.MyClient;
import com.exhibition.netty.client.MyClient.MessageListener;
import com.exhibition.service.ClientController;
import com.exhibition.utils.DataUtil;
import com.exhibition.utils.MobileConfig;
import com.exhibition.utils.NotificationUtil;
import com.google.gson.Gson;

public class HomeActivity extends Activity implements ActivityInterface,
		OnItemClickListener,MessageListener {
	private GridView mGridView;
	private EventData mEventData;
	private ArrayList<EventSchedule> mEventSchedules = new ArrayList<EventData.EventSchedule>();
	private ArrayList<Exhibitor> mExhibitors = new ArrayList<EventData.Exhibitor>();
	private ArrayList<Speaker> mSpeakers = new ArrayList<EventData.Speaker>();
	private int[] itemImgs = { R.drawable.schedule, R.drawable.exhibitors,
			R.drawable.speakers, R.drawable.about, R.drawable.setting,
			R.drawable.maps };  
	private String[] itemTexts = { "日程", "参展商", "嘉宾", "关于", "设置", "地图" };   
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	private MobileConfig mMobileConfig;
	private Gson gson = new Gson();
	private ClientController controller;  
	private double latitude;
	private double longitude;
	private String address = "";
	private boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page);  
		initData();
		findView();
		addAction();
		new Thread() {
			public void run() {
				linkSevice();
				try {
					sleep(3000);
					registSevice();  
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}  
				checkIn();
			};
		}.start(); 
	}

	private void initData() {
		mEventData = DataUtil.getEventData(this);
		mEventSchedules = DataUtil.getEventSchedules(this);
		mExhibitors = DataUtil.getExhibitors(this);
		mSpeakers = DataUtil.getSpeakers(this);
		mMobileConfig = MobileConfig.getMobileConfig(this);
		controller = ClientController.getController(this);
		latitude = getIntent().getDoubleExtra("latitude", 0.0);
		longitude = getIntent().getDoubleExtra("longitude", 0.0);
		address = getIntent().getStringExtra("address");
	}

	@Override
	public void findView() {
		mGridView = (GridView) findViewById(R.id.home_gridview);
		for (int i = 0; i < itemImgs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", itemImgs[i]);
			map.put("title", itemTexts[i]);
			data.add(map);
		}
		SimpleAdapter mGridViewAdapter = new SimpleAdapter(this,
				(List<Map<String, Object>>) data,
				R.layout.home_page_gridview_item, new String[] { "icon",
						"title" }, new int[] { R.id.gridview_img,
						R.id.gridview_text });

		mGridView.setAdapter(mGridViewAdapter);
	}
 
	@Override
	public void addAction() {
		mGridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent();
		switch (position) {
		case 0:
			it = new Intent(HomeActivity.this, SchShowActivity.class);
			it.putExtra("sch", mEventSchedules);
			it.putExtra("title", itemTexts[position]);
			startActivity(it);
			break;
		case 1:
			it = new Intent(HomeActivity.this, ExhibitorActivity.class);
			it.putExtra("exh", mExhibitors);
			it.putExtra("title", itemTexts[position]);
			startActivity(it);
			break;
		case 2:
			it = new Intent(HomeActivity.this, SpeakerActivity.class);
			it.putExtra("spea", mSpeakers);
			it.putExtra("title", itemTexts[position]);
			startActivity(it);
			break;
		case 3:
			it = new Intent(HomeActivity.this, AboutActivity.class);
			it.putExtra("detail", mEventData.getDescription());
			it.putExtra("title", itemTexts[position]);
			startActivity(it);
			break;
		case 4:
			linkSevice();
			it = new Intent(HomeActivity.this, ConfigActivity.class);
			startActivity(it);
			break;
		case 5:
			it = new Intent(HomeActivity.this, MapActivity.class);
			startActivity(it);
			break;
		}
	}
  
	private void linkSevice() {
		if (XmlDB.getInstance(this)
				.getKeyStringValue(StringPools.mServiceToken, "").equals("")) {
			MyClient client = new MyClient();
			ClientData data = new ClientData();
			data.setMacAddress(mMobileConfig.getLocalMacAddress());
			data.setAppCode("CCBN");
			String startupMessage = gson.toJson(data);
			client.send(startupMessage, "192.168.0.101", 8888);
			flag = false;
			System.out.println("----------------clientStart-----------------");
		}
	}

	private void registSevice() {
		try {
			if (!XmlDB.getInstance(this)
					.getKeyStringValue(StringPools.mServiceToken, "")
					.equals(""))
				controller.getService().registService(
						XmlDB.getInstance(this).getKeyStringValue(
								StringPools.mServiceToken, ""), "CCBN",
						"ANDROID");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void checkIn() {
		try {
			System.out.println("21312321312312321"
					+ XmlDB.getInstance(this).getKeyStringValue(
							StringPools.mServiceToken, ""));
			controller
					.getService()
					.checkIn(
							XmlDB.getInstance(this).getKeyStringValue(
									StringPools.mServiceToken, ""),
							"CCBN", latitude,longitude, address);
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessageReceived(MessageEvent e) {
		System.out.println("2131312321321313131321======================"+e.getMessage());
		NotificationUtil.testNotification(this, getIntent(), "kakakakaka");
	}
	
	
}
