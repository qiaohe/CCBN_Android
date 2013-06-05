package com.exhibition;

import com.exhibition.interfaces.ActivityInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ScheduleDetailActivity extends Activity implements ActivityInterface {
	private TextView tvDate;  //显示日程日期
	private TextView tvDatail;  //显示日程详细
	private TextView tvTitle;  
	private String date; 
	private String detail; 
	private Button btHome;   //返回到程序主界面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schedule_detail);
		
		initData();
		findView();  
		addAction(); 
	}
	
	public void initData(){  
		date = getIntent().getStringExtra("date");
		detail = getIntent().getStringExtra("detail");
	} 
	
	@Override
	public void findView() {
		tvDate = (TextView) this.findViewById(R.id.activity_schedule_detail_date);
		tvDatail = (TextView) this.findViewById(R.id.activity_schedule_detail_detail);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
	}
	@Override
	public void addAction() {  
		tvDate.setText(date);
		tvDatail.setText(detail);
		tvTitle.setText("日程详细");
		btHome = (Button) this.findViewById(R.id.home_button_second);
		btHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ScheduleDetailActivity.this, HomeActivity.class);
				startActivity(it);
				finish();   
			}
		});
	}
}
