package com.exhibition;

import java.util.List;
import java.util.Map;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;
import com.exhibition.utils.Resources;
import com.exhibition.utils.Tools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * 显示收到的所有消息列表窗体
 * @author pjq
 *
 */
public class MessageActivity extends Activity implements ActivityInterface {
	private ListView lvMessage;
	private Button btHome;
	private TextView tvTitle;
	private List<Map<String,Object>> messageList;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message); 
		initData();
		findView();
		addAction();
	}
	
	public void initData(){
		
	}
	
	@Override
	public void findView() {
		btHome = (Button) this.findViewById(R.id.home_button_second);
		lvMessage = (ListView) this.findViewById(R.id.activity_message_lv);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
	}
	@Override
	public void addAction()  {    
		btHome.setOnClickListener(new HomeClickListener(this));
		
		try{
			messageList = Tools.getMessage(this);
		}catch(Exception e){ 
		}
		if(null != messageList){
			lvMessage.setAdapter(new SimpleAdapter(getApplicationContext(),
	                messageList,
	                R.layout.listview_item,
	                new String[]{"timeAndContent"},   
	                new int[]{R.id.item_text}));
		}
		tvTitle.setText(R.string.message);  
	}
}
