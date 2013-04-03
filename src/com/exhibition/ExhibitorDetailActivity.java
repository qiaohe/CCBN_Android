package com.exhibition;

import com.exhibition.interfaces.ActivityInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ExhibitorDetailActivity extends Activity implements ActivityInterface{
	private String company;   //公司名
	private String location;  //公司地址
	private TextView tvOne;
	private TextView tvTwo;
	private TextView tvThree;
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
		company = getIntent().getStringExtra("company");
		location = getIntent().getStringExtra("location");
	}
	
	@Override
	public void findView() {
		tvOne = (TextView) this.findViewById(R.id.exhibitor_detail_tv_one);
		tvTwo = (TextView) this.findViewById(R.id.exhibitor_detail_tv_two);
		tvThree = (TextView) this.findViewById(R.id.exhibitor_detail_tv_three);
	}

	@Override
	public void addAction() {
		tvOne.setText(company + "\n" + location);
		
	}
	
	
}
