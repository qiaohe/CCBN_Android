package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.utils.DateFormatUtil;

public class SchShowActivity extends Activity implements 
		ActivityInterface, OnItemClickListener {
	private ListView mSchShowListView;
	private SchShowListViewAdapter mShowAdapter;
	private List<EventSchedule> mEventSchedules = new ArrayList<EventSchedule>();
	private List<Exhibitor> mExhibitors = new ArrayList<Exhibitor>();
	private List<Map<String, String>> dataes = new ArrayList<Map<String, String>>();
	private List<String> mDates = new ArrayList<String>();
	private List<String> mThemes = new ArrayList<String>();
	private List<String> mDetails = new ArrayList<String>();
	private String titleStr = "";
	private TextView mTitleText;
	private Button mHomeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sch_show_page);
		initData();
		findView();
		addAction();

	}

	@SuppressWarnings("unchecked")
	public void initData() {
		mEventSchedules = (List<EventSchedule>) getIntent().getExtras().get(
				"sch");
		//mExhibitors = (List<Exhibitor>) getIntent().getExtras().get("exh");

		//mDetails = getIntent().getExtras().getStringArrayList("detail");
		titleStr = getIntent().getStringExtra("title");
		if (mEventSchedules != null) {
			getEventTheme();
			getEventDate();
			for (int i = 0; i < mThemes.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("schTheme", mThemes.get(i));
				map.put("schDate", mDates.get(i));
				dataes.add(map);
			}
		} else if (mExhibitors != null) {
			for (int i = 0; i < mExhibitors.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("exhCompany", mExhibitors.get(i).getCompany());
				map.put("exhLoca", mExhibitors.get(i).getLocation());
				dataes.add(map);
			}
		}
	}

	private void getEventDate() {
		for (int i = 0; i < mEventSchedules.size(); i++) {
			mDates.add(DateFormatUtil.getWeekTimeByMillisecond(mEventSchedules
					.get(i).getDateFrom() + ""));
		}
	}

	private void getEventTheme() {
		for (int i = 0; i < mEventSchedules.size(); i++) {
			mThemes.add(mEventSchedules.get(i).getName());
		}
	}

	@Override
	public void findView() {
		mTitleText = (TextView) findViewById(R.id.title_text);
		mTitleText.setText(titleStr);
		mHomeBtn = (Button) findViewById(R.id.home_button);
		mHomeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToHomePage();
			}
		});
		mSchShowListView = (ListView) findViewById(R.id.schedule_show_listview);
		mShowAdapter = new SchShowListViewAdapter(this, dataes);
		mSchShowListView.setAdapter(mShowAdapter);
	}

	@Override
	public void addAction() {
		mSchShowListView.setOnItemClickListener(this);
	}

	private void goToHomePage() {
		Intent it = new Intent(SchShowActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}

	class SchShowListViewAdapter extends BaseAdapter {
		private List<Map<String, String>> data;
		private LayoutInflater mInflater;

		public SchShowListViewAdapter(Context context,
				List<Map<String, String>> dataes) {
			this.data = dataes;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = this.mInflater.inflate(
						R.layout.sch_show_listview_item, null);
				holder.mSchShowImg = (ImageView) convertView
						.findViewById(R.id.showitem_icon);
				holder.mSchShowText = (TextView) convertView
						.findViewById(R.id.texts);
				holder.mSchShowLog = (TextView) convertView
						.findViewById(R.id.showitem_catalog);
				holder.mSchShowLogBar = (LinearLayout) convertView
						.findViewById(R.id.showitem_catalogbar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String schDatelog = null;
			String lastSchDatelog = null;
			if (null != data.get(position).get("schDate")) {
				schDatelog = data.get(position).get("schDate");
			} else {
				schDatelog = data.get(position).get("exhLoca").substring(0, 1);  
			}
			if (position == 0) {
				holder.mSchShowLogBar.setVisibility(View.VISIBLE);
				holder.mSchShowLog.setText(schDatelog);
			} else {
				if (null != data.get(position).get("schDate")) {
					lastSchDatelog = data.get(position - 1).get("schDate");
				} else {
					lastSchDatelog = data.get(position - 1).get("exhLocation")
							.substring(0, 1);
				}
				if (schDatelog.equals(lastSchDatelog)) {
					holder.mSchShowLogBar.setVisibility(View.GONE);
				} else {
					holder.mSchShowLogBar.setVisibility(View.VISIBLE);
					holder.mSchShowLog.setText(schDatelog);
				}
			}
			if (null != data.get(position).get("schDate")) {
				holder.mSchShowText.setText(data.get(position).get("schTheme"));
			} else {
				holder.mSchShowText.setText(data.get(position)
						.get("exhCompany"));
			}

			return convertView;
		}

		public class ViewHolder {
			ImageView mSchShowImg;
			TextView mSchShowText;
			LinearLayout mSchShowLogBar;
			TextView mSchShowLog;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent(SchShowActivity.this, ScheduleDetailActivity.class);
		it.putExtra("date", mDates.get(position));
		it.putExtra("detail",mEventSchedules.get(position).getDescription());
		startActivity(it);
		
	}
}