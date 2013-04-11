package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.MessageEvent;

import android.app.Activity;
import android.content.Intent; 
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.exhibition.entities.EventData;
import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.entities.EventData.Speaker;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.netty.client.MyClient.MessageListener;
import com.exhibition.service.ClientController;
import com.exhibition.service.SocketService;
import com.exhibition.utils.DataUtil;
import com.exhibition.utils.NotificationUtil;

public class HomeActivity extends Activity implements ActivityInterface,
		OnItemClickListener,MessageListener {
	private GridView mGridView;
	private EventData mEventData;
	private ArrayList<EventSchedule> mEventSchedules = new ArrayList<EventData.EventSchedule>();
	private ArrayList<Exhibitor> mExhibitors = new ArrayList<EventData.Exhibitor>();
	private ArrayList<Speaker> mSpeakers = new ArrayList<EventData.Speaker>();
	private int[] itemImgs = { R.drawable.schedule, R.drawable.exhibitors,
			R.drawable.speakers, R.drawable.about, R.drawable.setting,
			R.drawable.maps, R.drawable.message};  
	private String[] itemTexts = { "日程", "参展商", "嘉宾", "关于", "设置", "地图","消息" };   
	private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();  
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
		socketLink(); 
	}

	private void socketLink() {
		//打开socket连接服务
		Intent intent = new Intent(this,SocketService.class);
		intent.putExtra("latitude", latitude);
		intent.putExtra("longitude", longitude);
		intent.putExtra("address", address);
		startService(intent);
	}

	private void initData() {
		mEventData = DataUtil.getEventData(this);
		mEventSchedules = DataUtil.getEventSchedules(this);
		mExhibitors = DataUtil.getExhibitors(this);
		mSpeakers = DataUtil.getSpeakers(this); 
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
			it = new Intent(HomeActivity.this, ConfigActivity.class);
			startActivity(it);
			break;
		case 5:
			it = new Intent(HomeActivity.this, MapActivity.class);
			startActivity(it);
			break;
		case 6:
			it = new Intent(HomeActivity.this, MessageActivity.class);
			startActivity(it);
			break;
		}
	}
  
	

	@Override
	public void onMessageReceived(MessageEvent e) {
		System.out.println("2131312321321313131321======================"+e.getMessage());
		NotificationUtil.testNotification(this, getIntent(), "kakakakaka");  
	}
	
	
}
