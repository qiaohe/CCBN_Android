package com.exhibition;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class VideoActivity extends Activity implements ActivityInterface {
	private TextView tvTitle;
	private Button btHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		findView();
		addAction(); 
	}
	@Override
	public void findView() {
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
	}
	@Override
	public void addAction() {
		tvTitle.setText(getIntent().getStringExtra("title"));
		btHome.setOnClickListener(new HomeClickListener(VideoActivity.this));
		
	}
	
}
