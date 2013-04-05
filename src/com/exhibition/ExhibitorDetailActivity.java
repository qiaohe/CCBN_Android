package com.exhibition;

import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.interfaces.ActivityInterface;

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
	private Button buttonBack;
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
	}
	
	@Override
	public void findView() {
		tvOne = (TextView) this.findViewById(R.id.exhibitor_detail_tv_one);
		tvTwo = (TextView) this.findViewById(R.id.exhibitor_detail_tv_two);
		tvThree = (TextView) this.findViewById(R.id.exhibitor_detail_tv_three);
		buttonBack= (Button) this.findViewById(R.id.exhibitor_detail_home_button);
	}

	@Override
	public void addAction() {
		tvOne.setText("公司名称：" + exhibitor.getCompany() + "\n" + 
					  "展       位：" + exhibitor.getLocation());
		tvTwo.setText("公司地址：" + exhibitor.getAddress() + "\n" + 
					  "联系电话：" + exhibitor.getPhone() + "\n" + 
				      "网       址：" + exhibitor.getWebsite());
		tvThree.setText(exhibitor.getDescription());
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ExhibitorDetailActivity.this, HomeActivity.class);
				startActivity(it);
				finish();
			}
		});
		
	}  
	
}
