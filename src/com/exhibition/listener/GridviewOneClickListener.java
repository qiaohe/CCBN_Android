package com.exhibition.listener;

import java.util.ArrayList;

import com.exhibition.AboutActivity;
import com.exhibition.ConfigActivity;
import com.exhibition.ExhibitorActivity; 
import com.exhibition.MapActivity;
import com.exhibition.MessageActivity;
import com.exhibition.NewsActivity;
import com.exhibition.QRCodeActivity;
import com.exhibition.SchShowActivity;
import com.exhibition.SpeakerActivity;
import com.exhibition.TrafficActivity;
import com.exhibition.VideoActivity;
import com.exhibition.entities.EventData;
import com.exhibition.entities.NewsData;
import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.entities.EventData.Speaker;
import com.exhibition.entities.NewsData.News;
import com.exhibition.utils.DataUtil;  
import android.content.Context;
import android.content.Intent; 
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class GridviewOneClickListener implements OnItemClickListener {
	private Context context;
	private EventData eventData;
	private ArrayList<EventSchedule> eventSchedules = new ArrayList<EventData.EventSchedule>();
	private ArrayList<Exhibitor> exhibitors = new ArrayList<EventData.Exhibitor>();
	private ArrayList<Speaker> speakers = new ArrayList<EventData.Speaker>();
	private String[] itemTexts;
	private NewsData newsData;  
	public GridviewOneClickListener(Context context,String[] itemTexts) { 
		this.context = context;
		this.itemTexts = itemTexts;
		eventData = DataUtil.getEventData(context);
		eventSchedules = DataUtil.getEventSchedules(context);
		exhibitors = DataUtil.getExhibitors(context);
		speakers = DataUtil.getSpeakers(context); 
		newsData = DataUtil.getNewsData(context);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent();
		switch (position) {
		case 0:
			it = new Intent(context, SchShowActivity.class);
			it.putExtra("sch", eventSchedules);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		case 1:
			it = new Intent(context, ExhibitorActivity.class);
			it.putExtra("exh", exhibitors);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		case 2:
			it = new Intent(context, SpeakerActivity.class);
			it.putExtra("spea", speakers);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		case 3:
			it = new Intent(context, AboutActivity.class);
			it.putExtra("detail", eventData.getDescription());  
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		case 4:
			it = new Intent(context, ConfigActivity.class);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);   
			break;
		case 5:
			it = new Intent(context, NewsActivity.class);
			it.putExtra("title", itemTexts[position]);
			it.putExtra("newsData", newsData);    
			context.startActivity(it);
			
			break;
		case 6:
			it = new Intent(context, MessageActivity.class);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);  
			break;
		case 7:
			it = new Intent(context, VideoActivity.class);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		case 8:
			it = new Intent(context, QRCodeActivity.class);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		case 9:
			it = new Intent(context, TrafficActivity.class);
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		}
	}

}
