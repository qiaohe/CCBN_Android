package com.exhibition;

import java.util.Map;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.exhibition.conts.StringPools;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MapActivity extends Activity implements ActivityInterface{
	private Button btHome;
	private TextView tvTitle;
	private BMapManager bmapManager;
	private MapView mapView;
	private ImageView imageview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		/*bmapManager = new BMapManager(getApplicationContext());  
		bmapManager.init(StringPools.baiDuMapStrKey, null);
		
		setContentView(R.layout.activity_map);
		mapView = (MapView) this.findViewById(R.id.activity_map_mapView);   
		mapView.setBuiltInZoomControls(true);
		MapController mapController = mapView.getController();
		GeoPoint point = new GeoPoint((int)(30.915* 1E6),(int)(121.104* 1E6));
		mapController.setCenter(point);
		mapController.setZoom(10);
		btHome = (Button) this.findViewById(R.id.home_button_second);
		btHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MapActivity.this,HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);  
		tvTitle.setText(R.string.map);*/
		
		setContentView(R.layout.activity_map);
		
		
		
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void findView() {
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
		btHome = (Button) this.findViewById(R.id.home_button_second);
	}
	@Override
	public void addAction() {
		tvTitle.setText(R.string.map);
		btHome.setOnClickListener(new HomeClickListener(this));
	}
	
}
