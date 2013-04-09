package com.exhibition;

import com.exhibition.listener.HomeClickListener;
import com.exhibition.utils.Resources;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 显示收到的所有消息列表窗体
 * @author pjq
 *
 */
public class MessageActivity extends Activity {
	private ListView lvMessage;
	private Button btHome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		btHome = (Button) this.findViewById(R.id.home_button_second);
		btHome.setOnClickListener(new HomeClickListener(this));
		
		lvMessage = (ListView) this.findViewById(R.id.activity_message_lv);
		lvMessage.setAdapter(new SimpleAdapter(getApplicationContext(),
				                               Resources.messageMap,
				                               R.layout.listview_item,
				                               new String[]{"timeAndContent"},
				                               new int[]{R.id.item_text}));
	}
}
