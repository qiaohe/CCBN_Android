package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exhibition.entities.NewsData;
import com.exhibition.entities.NewsData.News;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class NewsActivity extends Activity 
			implements ActivityInterface,OnItemClickListener {
	private ListView listView;
	private NewsData newsData;
	private SimpleAdapter adapter;
	private Button btHome;
	private TextView tvTitle;
	private String title;
	private News[] newses;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news);
		
		initData();
		findView();
		addAction();   
	}  
	public void initData(){
		newsData =  (NewsData) getIntent().getExtras().get("newsData");
		newses = newsData.getNewses();
		title = (String) getIntent().getStringExtra("title");
		List<Map<String,Object>> newsTitle = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < newses.length; i++){ 
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", newses[i].getTitle());    
			newsTitle.add(map);    
		} 
		adapter = new SimpleAdapter(this,
									(List<Map<String,Object>>)newsTitle,  
									R.layout.listview_item,    
									new String[]{"title"},    
									new int[]{R.id.item_text});    
	}
	  
	@Override 
	public void findView() {
		listView = (ListView) this.findViewById(R.id.activity_news_listview); 
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
	}   
	
	@Override  
	public void addAction() {   
		btHome.setOnClickListener(new HomeClickListener(this));
		tvTitle.setText(title);
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(this);   
	}
	
	@Override  
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(NewsActivity.this,NewsDetailActivity.class);
		intent.putExtra("detail", newsData.getNewses()[position].getContent());
		startActivity(intent);
	}        
}
