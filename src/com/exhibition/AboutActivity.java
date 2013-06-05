package com.exhibition;


import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;
import com.exhibition.utils.Tools;

import android.app.Activity; 
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;  
import android.widget.TextView;

public class AboutActivity extends Activity implements ActivityInterface {
	private Button btHome;
	private TextView tvTitle;
	private String titleStr="";   
	private String aboutDetail;  
	private WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_page);
		initData();  
		findView();    
		addAction();  
	}
	
	public void initData() {  
		titleStr = getIntent().getStringExtra("title");
		aboutDetail = Tools.transformToHtml(getIntent().getStringExtra("detail"));
	} 
	
	@Override
	public void findView() {  
		webview = (WebView) this.findViewById(R.id.about_page_webview); 
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);  
	} 
	
	@Override
	public void addAction() {   
		webview.loadDataWithBaseURL("",aboutDetail, "text/html", "utf-8","");  
		btHome.setOnClickListener(new HomeClickListener(this));
		tvTitle.setText(titleStr);  
	}
	
}
