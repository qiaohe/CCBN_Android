package com.exhibition.listener;


import com.exhibition.TrafficActivity;  
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
