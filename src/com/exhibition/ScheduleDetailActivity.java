package com.exhibition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ScheduleDetailActivity extends Activity {
	private TextView tvDate;  //显示日程日期
	private TextView tvDatail;  //显示日程详细
	private String date; 
	private String detail;
	private Button btHome;   //返回到程序主界面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schedule_detail);
		
		tvDate = (TextView) this.findViewById(R.id.activity_schedule_detail_date);
		tvDatail = (TextView) this.findViewById(R.id.activity_schedule_detail_detail);
		
		date = getIntent().getStringExtra("date");
		detail = getIntent().getStringExtra("detail");
		
		tvDate.setText(date);
		tvDatail.setText(detail);
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
