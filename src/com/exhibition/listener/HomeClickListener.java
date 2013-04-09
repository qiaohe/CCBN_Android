package com.exhibition.listener;

import com.exhibition.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeClickListener implements OnClickListener {

	private Context context;
	
	public HomeClickListener(Context context) {
		
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context,HomeActivity.class);
		context.startActivity(intent);
	}

}
