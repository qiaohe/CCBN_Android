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

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.utils.ListUtil;

public class SchDateActivity extends Activity implements ActivityInterface {
	private ListView mSchDateListView;
	private List<Map<String, Object>> mSchDates = new ArrayList<Map<String, Object>>();
	private List<String> mEventDates = new ArrayList<String>();
	private SimpleAdapter mListViewAdapter;
	private String mTitleText;
	private TextView mTitle;
	private Button mHomeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.schedule_page);
		initData();
		findView();
		addAction();
	}

	@Override
	public void findView() {
		mTitle = (TextView) findViewById(R.id.title_text);
		mHomeBtn = (Button) findViewById(R.id.home_button);
		mHomeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToHomePage();
			}
		});
		mSchDateListView = (ListView) findViewById(R.id.schedule_listview);
		mListViewAdapter = new SimpleAdapter(this, mSchDates, R.layout.listview_item,
				new String[] { "schDate" }, new int[] { R.id.item_text });
		mSchDateListView.setAdapter(mListViewAdapter);
	}

	@Override
	public void addAction() {
		mTitle.setText(mTitleText);
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		mTitleText = getIntent().getStringExtra("title");
		mEventDates = ListUtil
				.removeDuplicateWithOrder((List<String>) getIntent()
						.getExtras().get("date"));
		for(int i = 0;i<mEventDates.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("schDate", mEventDates.get(i));
			mSchDates.add(map);
		}
	}
	
	private void goToHomePage() {
		Intent it = new Intent(SchDateActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}
}
