package com.exhibition;

import java.util.HashMap;
import java.util.Map;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;
import com.exhibition.utils.Tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button; 
import android.widget.TextView;

public class ConfigActivity extends Activity implements ActivityInterface {
	private Button btHome;
	private Button btUpdate;
	private Button btChangefont;
	private TextView tvTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_config);
		
		initData();
		findView();
		addAction();
	} 
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void findView() {
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
		btHome = (Button) this.findViewById(R.id.home_button_second);
		btUpdate = (Button) this.findViewById(R.id.activity_config_bt_update);
		btChangefont = (Button) this.findViewById(R.id.activity_config_bt_changefont);
	}
	@Override
	public void addAction() {
		tvTitle.setText(R.string.config);
		btHome.setOnClickListener(new HomeClickListener(ConfigActivity.this));
		btUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new  AlertDialog.Builder(ConfigActivity.this)
				.setTitle("检查更新")
				.setMessage("没有可用的版本更新")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
					}
				}).show(); 
			}
		});
		
		btChangefont.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*new AlertDialog.Builder(ConfigActivity.this)
				.setTitle("设置字体")
				.setIcon(android.R.drawable.ic_dialog_info)  
				.setSingleChoiceItems(new String[]{"1","2","3"}, 0,null).show();*/  
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("timeAndContent", "pjq");
				Tools.saveMessage(map,getApplicationContext()); 
			}  
		});
	}
}  
