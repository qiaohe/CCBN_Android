package com.exhibition;

import com.exhibition.entities.EventData;
import com.exhibition.interfaces.ActivityInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity implements ActivityInterface {
	private TextView mTitle;
	private ImageView mSpeakerPhoto;
	private TextView mAboutContent;
	private Button mHomeBtn;
	private String titleStr="";
	private String aboutDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_page);
		initData();
		findView();
		addAction();
	}
	private void initData() {
		titleStr = getIntent().getStringExtra("title");
		aboutDetail = getIntent().getStringExtra("detail");
	}
	@Override
	public void findView() {
		mTitle = (TextView) findViewById(R.id.title_text);
		mAboutContent = (TextView)findViewById(R.id.about_content);
		mHomeBtn = (Button) findViewById(R.id.home_button);
	}
	@Override
	public void addAction() {
		mTitle.setText(titleStr);
		mAboutContent.setText(aboutDetail);
		
		mHomeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToHomePage();
			}

		});
	}
	private void goToHomePage() {
		Intent it = new Intent(AboutActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}


}
