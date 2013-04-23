package com.exhibition.listener;

import java.util.ArrayList;

import com.exhibition.AboutActivity;
import com.exhibition.ConfigActivity;
import com.exhibition.ExhibitorActivity;
import com.exhibition.HomeActivity;
import com.exhibition.MapActivity;
import com.exhibition.MessageActivity;
import com.exhibition.QRCodeActivity;
import com.exhibition.SchShowActivity;
import com.exhibition.SpeakerActivity;
import com.exhibition.TrafficActivity;
import com.exhibition.VideoActivity;
import com.exhibition.entities.EventData;
import com.exhibition.entities.EventData.EventSchedule;
import com.exhibition.entities.EventData.Exhibitor;
import com.exhibition.entities.EventData.Speaker;
import com.exhibition.utils.DataUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class GridviewTwoClickListener implements OnItemClickListener {
	private Context context;  
	private String[] itemTexts;
	public GridviewTwoClickListener(Context context,String[] itemTexts) { 
		this.context = context;
		this.itemTexts = itemTexts;  
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent it = new Intent();
		switch (position) {
		case 0:
			it = new Intent(context, TrafficActivity.class);  
			it.putExtra("title", itemTexts[position]);
			context.startActivity(it);
			break;
		
		}
	}
}
