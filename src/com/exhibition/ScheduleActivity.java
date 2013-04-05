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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.utils.DateFormatUtil;

public class ScheduleActivity extends Activity implements ActivityInterface,
		OnItemClickListener {
	private ListView mScheduleListView;
	private TextView mTitle;
	private Button mHomeBtn;
	private String mPageTitle;
	private ArrayList<EventSchedule> mEventSchedules = new ArrayList<EventSchedule>();
	private EventSchedule mEventSchedule;
	private String[] mTitles = { "会展日期", "会展主题", "行程简介" };
	private List<Map<String, Object>> mSchedules = new ArrayList<Map<String, Object>>();
	private ArrayList<String> mThemes = new ArrayList<String>();
	private ArrayList<String> mDates = new ArrayList<String>();
	private ArrayList<String> mDetails = new ArrayList<String>();

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
		mScheduleListView = (ListView) findViewById(R.id.schedule_listview);
		for (int i = 0; i < mTitles.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", mTitles[i]);
			mSchedules.add(map);
		}
		SimpleAdapter mGridViewAdapter = new SimpleAdapter(this,
				(List<Map<String, Object>>) mSchedules, R.layout.listview_item,
				new String[] { "title" }, new int[] { R.id.item_text });

		mScheduleListView.setAdapter(mGridViewAdapter);
	}

	@Override
	public void addAction() {
		mScheduleListView.setOnItemClickListener(this);
		mHomeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.home_button:
					goToHomePage();
					break;  
				default:
					break;
				}
			}

		});
		mTitle.setText(mPageTitle);
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		mPageTitle = getIntent().getStringExtra("title");
		mEventSchedules = (ArrayList<EventSchedule>) getIntent().getExtras()
				.get("sch");
		getEventDate();
		getEventTheme();
		getEventDescription();
	}
	private void getEventDate() {
		for (int i = 0; i < mEventSchedules.size(); i++) {
			mDates.add(DateFormatUtil.getDateTimeByMillisecond(mEventSchedules
					.get(i).getDateFrom() + ""));
		}
	}
	private void getEventTheme() {
		for (int i = 0; i < mEventSchedules.size(); i++) {
			mThemes.add(mEventSchedules.get(i).getName());
		}
	}
	private void getEventDescription() {
		for (int i = 0; i < mEventSchedules.size(); i++) {
			mDetails.add(mEventSchedules.get(i).getDescription());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent();
		switch (position) {
		case 0:
			it = new Intent(ScheduleActivity.this, SchDateActivity.class);
			it.putExtra("date", mDates);
			it.putExtra("title", mTitles[position]);
			startActivity(it);
			break;
		case 1:
			it = new Intent(ScheduleActivity.this, SchShowActivity.class);
			it.putExtra("theme", mThemes);
			it.putExtra("sch", mEventSchedules);
			it.putExtra("title", mTitles[position]);
			startActivity(it);
			break;
		case 2:
			it = new Intent(ScheduleActivity.this, SchShowActivity.class);
			it.putExtra("detail", mDetails);
			it.putExtra("sch", mEventSchedules);
			it.putExtra("title", mTitles[position]);
			startActivity(it);
			break;
		default:
			break;
		}
	}

	private void goToHomePage() {
		Intent it = new Intent(ScheduleActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}

}
