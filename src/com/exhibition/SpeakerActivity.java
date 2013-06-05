package com.exhibition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.exhibition.entities.EventData.Speaker;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.utils.PingYinUtil;
import com.exhibition.utils.PinyinComparator;
import com.exhibition.widgets.SideBar;

public class SpeakerActivity extends Activity implements ActivityInterface,OnScrollListener, OnItemClickListener{
	private TextView mTitle;
	private Button mHomeBtn;
	private String titleStr;
	private List<Speaker> mSpeakers = new ArrayList<Speaker>();
	private SpeakerAdapter mSpeakerAdapter;
	private List<Map<String,String>> dataes = new ArrayList<Map<String,String>>();
	
	private Map<Integer,Object> newMap = new HashMap<Integer, Object>();
	private ListView mSpeakerListView;
	private SideBar mIndexBar;
	private WindowManager mWindowManager;
	private TextView txtOverlay; // 	用来放在WindowManager中显示提示字符
	public static String[] mNicks;
	private int scrollState; // 滚动的状态
	private Handler handler;
	private DisapearThread disapearThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.speaker_page);
		
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		handler = new Handler();
		disapearThread = new DisapearThread();
		
		initData();
		findView();
		addAction();
	}

	@SuppressWarnings("unchecked")
	public void initData() {
		mSpeakers = (List<Speaker>) getIntent().getExtras().get("spea");
		System.out.println("111111111111======="+mSpeakers.size());
		for(int i=0;i<mSpeakers.size();i++){
			Map<String,String> map = new HashMap<String, String>();
			map.put("photo", mSpeakers.get(i).getPhoto());
			map.put("name", mSpeakers.get(i).getName());
			map.put("company", mSpeakers.get(i).getCompany());
			dataes.add(map);
		}
		titleStr = getIntent().getStringExtra("title");
	}

	@Override
	public void findView() {
		mTitle = (TextView) findViewById(R.id.title_text);
		mHomeBtn = (Button) findViewById(R.id.home_button);
		mSpeakerListView = (ListView) findViewById(R.id.speaker_page_listview);
		mSpeakerAdapter = new SpeakerAdapter(this, dataes);
		mSpeakerListView.setAdapter(mSpeakerAdapter);
		mIndexBar = (SideBar) findViewById(R.id.sideBar);
		mIndexBar.setListView(mSpeakerListView);
		txtOverlay = (TextView) LayoutInflater.from(this).inflate(R.layout.list_position, null);
		txtOverlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
		mWindowManager.addView(txtOverlay, lp);
		mIndexBar.setTextView(txtOverlay);
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
		mSpeakerListView.setOnScrollListener(this);
		mSpeakerListView.setOnItemClickListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mNicks != null && mNicks.length > 0)
			txtOverlay.setText(String.valueOf(PingYinUtil.converterToFirstSpell(mNicks[firstVisibleItem + (visibleItemCount >> 1)]).charAt(0)).toUpperCase());
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.scrollState = scrollState;
		if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE)
		{
			handler.removeCallbacks(disapearThread);
			// 提示延迟1s再消失
			handler.postDelayed(disapearThread, 1000);
		} else
		{
			txtOverlay.setVisibility(View.VISIBLE);
		}
	}
	private void goToHomePage() {
		Intent it = new Intent(SpeakerActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}
	private class DisapearThread implements Runnable {
		public void run() {
			// 避免在1.5s内，用户再次拖动时提示框又执行隐藏命令。
			if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE)
			{
				txtOverlay.setVisibility(View.INVISIBLE);
			}
		}
	}
	class SpeakerAdapter extends BaseAdapter implements SectionIndexer {
		private LayoutInflater mInflater;
		private List<Map<String, String>> data;
		private Map<String,String> nameMap = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		public SpeakerAdapter(Context context, List<Map<String, String>> dataes)
		{
			this.data = dataes;
			mInflater = LayoutInflater.from(context);
			if (data != null)
			{
				for(int i=0;i<data.size();i++){
					nameMap.put(data.get(i).get("name"), i+"");
				}
				Set<String> set = nameMap.keySet();
				mNicks = new String[set.size()];
				set.toArray(mNicks);
			}
			// 排序(实现了中英文混排)
			Arrays.sort(mNicks, new PinyinComparator());
		}

		@Override
		public int getCount() {
			return mNicks.length;
		}

		@Override
		public Object getItem(int position) {
			return mNicks[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int index = position;
			final String nickName = mNicks[index];
			newMap.put(position, data.get(position));
			ViewHolder viewHolder = null;
			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.speaker_page_listview_item, null);
				viewHolder = new ViewHolder();
				viewHolder.mSpeakerLog = (LinearLayout)convertView.findViewById(R.id.speakeritem_catalogbar);
				viewHolder.mSpeakerLogText = (TextView) convertView.findViewById(R.id.speakeritem_catalog);
				viewHolder.mSpeakerPhoto = (ImageView) convertView.findViewById(R.id.speakeritem_photo);
				viewHolder.mSpeakerName = (TextView) convertView.findViewById(R.id.speaker_names);
				viewHolder.mSpeakerCompany = (TextView) convertView.findViewById(R.id.speaker_company);
				convertView.setTag(viewHolder);
			} else
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			String catalog = null;
			String lastCatalog = null;
			catalog = PingYinUtil.converterToFirstSpell(nickName).substring(0, 1);
			if (position == 0)
			{
				viewHolder.mSpeakerLog.setVisibility(View.VISIBLE);
				viewHolder.mSpeakerLogText.setText(catalog);
			} else
			{
				lastCatalog = PingYinUtil.converterToFirstSpell(mNicks[index - 1]).substring(0, 1);
				if (catalog.equals(lastCatalog))
				{
					viewHolder.mSpeakerLog.setVisibility(View.GONE);
				} else
				{
					viewHolder.mSpeakerLog.setVisibility(View.VISIBLE);
					viewHolder.mSpeakerLogText.setText(catalog);
				}
			}
			viewHolder.mSpeakerPhoto.setImageResource(R.drawable.default_avatar);
			viewHolder.mSpeakerName.setText(nickName);
			viewHolder.mSpeakerCompany.setText(data.get(position).get("company"));
			return convertView;
		}

		private class ViewHolder {
			LinearLayout mSpeakerLog;// 目录
			TextView mSpeakerLogText;
			ImageView mSpeakerPhoto;// 头像
			TextView mSpeakerName;// 昵称
			TextView mSpeakerCompany;// 电话号
		}

		@Override
		public int getPositionForSection(int section) {
			for (int i = 0; i < mNicks.length; i++)
			{
				String l = PingYinUtil.converterToFirstSpell(mNicks[i]).substring(0, 1);
				char firstChar = l.toUpperCase().charAt(0);
				if (firstChar == section)
				{
					return i;
				}
			}
			return -1;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			return null;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent it = new Intent(SpeakerActivity.this, SpeakerDetailActivity.class);
		it.putExtra("speaker", mNicks[position]);
		it.putExtra("title", titleStr);
		startActivity(it);
	}
}
