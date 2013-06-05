package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import android.widget.SimpleAdapter;
import android.widget.TextView;  
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.interfaces.ActivityInterface;

public class ExhibitorActivity extends Activity implements 
		ActivityInterface,OnItemClickListener,OnScrollListener{
	private ListView mExhibitorListView;
	private List<Exhibitor> mExhibitors = new ArrayList<Exhibitor>();
	private List<Map<String,Object>> dataes =new ArrayList<Map<String,Object>>();
	private TextView mTitle;
	private Button mHomeBtn;
	private String strTitle;
	private LinearLayout linlLoad;
	private View viewFooter;
	private View viewListitem;
	private int listSize = 10;
	private ExhibitroListAdapter adapter;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(1 == msg.what){
				linlLoad.setVisibility(View.GONE);
				listSize += 5;
				if(listSize > mExhibitors.size()){
					listSize = mExhibitors.size();
				}
				adapter.notifyDataSetChanged();
			}
		}
	};
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
	public void initData() {
		//getExtras返回一个Map数据
		mExhibitors = (List<Exhibitor>) getIntent().getExtras().get("exh");
		strTitle = getIntent().getStringExtra("title");
		adapter = new ExhibitroListAdapter();
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
		viewFooter = LayoutInflater.from(this).inflate(R.layout.list_footer, null);
		viewListitem = LayoutInflater.from(this).inflate(R.layout.listview_item, null);
		linlLoad = (LinearLayout) viewFooter.findViewById(R.id.list_footer);  		
	}

	@Override
	public void addAction() {
		mTitle.setText(strTitle);
		mHomeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToHomePage();
			}
		});
		linlLoad.setVisibility(View.GONE);
		mExhibitorListView.setOnItemClickListener(this); 
		mExhibitorListView.addFooterView(viewFooter); 
		mExhibitorListView.setAdapter(adapter);
		mExhibitorListView.setOnScrollListener(this);
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
		it.putExtra("exhibitor", mExhibitors.get(position));
		it.putExtra("title", strTitle);
		startActivity(it);  
	}
	
	class ExhibitroListAdapter extends BaseAdapter{
		
		
		public ExhibitroListAdapter() { 
			
		}
		@Override
		public int getCount() {
			return listSize;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) { 
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textview;
			if(convertView == null){
				/*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT); 	
				textview = (TextView) viewListitem.findViewById(R.id.item_text);
				textview.setLayoutParams(params);*/
				textview = new TextView(ExhibitorActivity.this);
				textview.setTextSize(20);
				textview.setTextColor(Color.BLACK);
				/*TextPaint paint = textview.getPaint();
				paint.setFakeBoldText(true);*/
				textview.setBackgroundResource(R.drawable.mm_listitem_simple);
				textview.setGravity(Gravity.CENTER_VERTICAL);
			}else{
				textview = (TextView) convertView;
			}
			textview.setText(mExhibitors.get(position).getCompany());
			return textview;
		}  
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			if(view.getLastVisiblePosition() == view.getCount() - 1){
				linlLoad.setVisibility(View.VISIBLE);
				loadNextData();
			}
		}
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
	private void loadNextData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = handler.obtainMessage();
				message.what = 1;
				handler.sendMessage(message);
			}
		}).start();
	}
}
