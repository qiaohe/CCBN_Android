package com.exhibition;

import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ExhibitorDetailActivity extends Activity implements ActivityInterface{
	
	private Exhibitor exhibitor;     //参展商
	private TextView tvOne;
	private TextView tvTwo;
	private TextView tvThree;
	private Button btHome;
	private TextView tvTitle;
	private String strTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.exhibitor_detail);
		initData();
		findView();
		addAction();
	}

	private void initData() {
		exhibitor = (Exhibitor) getIntent().getExtras().get("exhibitor");
		strTitle = getIntent().getStringExtra("title");
	}
	
	@Override
	public void findView() {
		tvOne = (TextView) this.findViewById(R.id.exhibitor_detail_tv_one);
		tvTwo = (TextView) this.findViewById(R.id.exhibitor_detail_tv_two);
		tvThree = (TextView) this.findViewById(R.id.exhibitor_detail_tv_three);
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
	}

	@Override
	public void addAction() {
		tvOne.setText("公司名称：" + exhibitor.getCompany() + "\n" + 
					  "展       位：" + exhibitor.getLocation());
		tvTwo.setText("公司地址：" + exhibitor.getAddress() + "\n" + 
					  "联系电话：" + exhibitor.getPhone() + "\n" + 
				      "网       址：" + exhibitor.getWebsite());
		tvThree.setText(exhibitor.getDescription());
		tvTitle.setText(strTitle);
		btHome.setOnClickListener(new HomeClickListener(ExhibitorDetailActivity.this));
		
	}  
	
}
