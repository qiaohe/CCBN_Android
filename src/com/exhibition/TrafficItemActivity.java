package com.exhibition;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

public class TrafficItemActivity extends Activity implements ActivityInterface {
	private Button btHome;
	private TextView tvTitle;
	private String title;
	private ImageView ivImage;
	private TextView tvIntro;
	private int imageID;
	private String intro;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_traffic_item);
		initData();
		findView();
		addAction(); 
	}
	public void initData(){
		title = getIntent().getStringExtra("title");
		imageID = getIntent().getIntExtra("imageID", 0);
		intro = getIntent().getStringExtra("intro");
	}
	@Override
	public void findView() {
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
		tvIntro = (TextView) this.findViewById(R.id.activity_traffic_item_tv);
		ivImage = (ImageView) this.findViewById(R.id.activity_traffic_item_iv);
	}

	@Override
	public void addAction() {
		btHome.setOnClickListener(new HomeClickListener(this));
		tvTitle.setText(title);
		ivImage.setImageResource(imageID);
		tvIntro.setText(intro);
	}

}
