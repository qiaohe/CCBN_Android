package com.exhibition;

import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class MapActivity extends Activity {
	private Button btHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_map);
		btHome = (Button) this.findViewById(R.id.home_button_second);
		btHome.setOnClickListener(new HomeClickListener(MapActivity.this));
	}
}
