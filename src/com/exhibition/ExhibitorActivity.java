package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.interfaces.ActivityInterface;

public class ExhibitorActivity extends Activity implements 
		ActivityInterface,OnItemClickListener{
	private ListView mExhibitorListView;
	private List<Exhibitor> mExhibitors = new ArrayList<Exhibitor>();
	private List<Map<String,Object>> dataes =new ArrayList<Map<String,Object>>();
	private TextView mTitle;
	private Button mHomeBtn;
	private String titleStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.schedule_page);
		initData();
		findView();
		addAction();
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		mExhibitors = (List<Exhibitor>) getIntent().getExtras().get("exh");
		titleStr = getIntent().getStringExtra("title");
	}

	@Override
	public void findView() {
		mTitle = (TextView) findViewById(R.id.title_text);
		mHomeBtn = (Button) findViewById(R.id.home_button);
		mExhibitorListView = (ListView) findViewById(R.id.schedule_listview);
		for (int i = 0; i < mExhibitors.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("context", mExhibitors.get(i).getCompany());
			dataes.add(map);
		}
		SimpleAdapter mGridViewAdapter = new SimpleAdapter(this,
				(List<Map<String, Object>>) dataes, R.layout.listview_item,
				new String[] { "context" }, new int[] { R.id.item_text });
		
		mExhibitorListView.setAdapter(mGridViewAdapter);
	}

	@Override
	public void addAction() {
		mTitle.setText(titleStr);
		mHomeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToHomePage();
			}
		});
		
		mExhibitorListView.setOnItemClickListener(this);
	}
	private void goToHomePage() {
		Intent it = new Intent(ExhibitorActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent(ExhibitorActivity.this, ExhibitorDetailActivity.class);
		it.putExtra("company", mExhibitors.get(position).getCompany());
		it.putExtra("location", mExhibitors.get(position).getLocation());
		startActivity(it);  
	}
	
}
