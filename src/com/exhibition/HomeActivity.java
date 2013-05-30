package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent; 
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;   
import android.widget.ViewFlipper;    
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.GridviewOneClickListener;
import com.exhibition.listener.GridviewTwoClickListener; 
import com.exhibition.service.SocketService;

public class HomeActivity extends Activity implements ActivityInterface,
		OnGestureListener{ 
	private int[] itemImgs = { R.drawable.schedule, R.drawable.exhibitors,
			R.drawable.speakers, R.drawable.about, R.drawable.setting,
			R.drawable.maps, R.drawable.message,R.drawable.video,
			R.drawable.qrcode};  
	private String[] itemTexts = { "日程", "参展商", "嘉宾", "关于", 
				"设置", "地图","消息","视频","二维码"};   
	private int[] itemImgs2 = {R.drawable.traffic,R.drawable.message};
	private String[] itemTexts2 = {"交通","新闻"};
	private List<Map<String, Object>> gvOneData = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> gvTwoData = new ArrayList<Map<String, Object>>();
	private ViewFlipper viewFlipper;  
	private GridView gvOne;  
	private GridView gvTwo;
	private final static int SLIP_DISTANCE = 50;
	private GestureDetector geDetector;
	private boolean nextORPrevours = true;   //ture 为next
	private ImageView ivDianone,ivDiantwo;
	private Intent serviceIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page);  
		initData();
		findView();
		addAction();  
		socketLink(); 		  
	}

	private void socketLink() {
		//打开socket连接服务
		startService(serviceIntent);
	}

	private void initData() { 
		serviceIntent = new Intent(this,SocketService.class);
		for (int i = 0; i < itemImgs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", itemImgs[i]);
			map.put("title", itemTexts[i]);
			gvOneData.add(map); 
		}
		for (int i = 0; i < itemImgs2.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", itemImgs2[i]);
			map.put("title", itemTexts2[i]);
			gvTwoData.add(map);
		}
		geDetector = new GestureDetector(this); 
	}
	
	@Override
	public void findView() {    
		viewFlipper = (ViewFlipper) this.findViewById(R.id.home_page_viewflipper);
		ivDianone = (ImageView) this.findViewById(R.id.home_page_im_dianone);
		ivDiantwo = (ImageView) this.findViewById(R.id.home_page_im_diantwo);
		gvOne = createGridView(gvOneData);  
		gvTwo = createGridView(gvTwoData);  
	} 

	@Override
	public void addAction() {
		gvOne.setOnItemClickListener(new GridviewOneClickListener(this,itemTexts));
		gvTwo.setOnItemClickListener(new GridviewTwoClickListener(this,itemTexts2));
		viewFlipper.addView(gvOne);  
		viewFlipper.addView(gvTwo);   
	}  
	
	private GridView createGridView(List<Map<String, Object>> data) {
		GridView gridview = new GridView(HomeActivity.this){
			@Override
			public boolean onTouchEvent(MotionEvent ev) {
				geDetector.onTouchEvent(ev);
				return super.onTouchEvent(ev);
			}
		};
		gridview.setHorizontalSpacing(5);
		gridview.setVerticalSpacing(5);  
		gridview.setNumColumns(3);
		gridview.setPadding(10,10,10,10);
		SimpleAdapter adapter = new SimpleAdapter(HomeActivity.this, 
												 data,
												 R.layout.home_page_gridview_item,
												 new String[]{"icon","title"}, 
												 new int[]{R.id.gridview_img,R.id.gridview_text});
		gridview.setAdapter(adapter);
		return gridview;    
	}

	@Override
	public boolean onDown(MotionEvent e) {  
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {  
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) { 
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) { 
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
				
	}  
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e2.getX() - e1.getX() > 0 && e2.getX() - e1.getX() > SLIP_DISTANCE ){
			if(nextORPrevours){
				//overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				/*viewFlipper.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_to_left));
				viewFlipper.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_right));*/
				viewFlipper.showNext();
				nextORPrevours = false;
				ivDianone.setImageResource(R.drawable.page_indicator);
				ivDiantwo.setImageResource(R.drawable.page_indicator_focused);
				return true;
			}
		}else if(e1.getX() - e2.getX() > 0 && e1.getX() - e2.getX() > SLIP_DISTANCE){
			if(!nextORPrevours){
				//overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
				/*viewFlipper.setAnimation(AnimationUtils.loadAnimation(this,R.anim.out_to_right));
				viewFlipper.setAnimation(AnimationUtils.loadAnimation(this,R.anim.in_from_left));*/
				viewFlipper.showPrevious();
				nextORPrevours = true;
				ivDiantwo.setImageResource(R.drawable.page_indicator);
				ivDianone.setImageResource(R.drawable.page_indicator_focused);
				return true;
			}
		}
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		geDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) { 
		if(event.getAction() == KeyEvent.ACTION_DOWN &&
				event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(HomeActivity.this)
			.setTitle("注意")
			.setMessage("您确定要退出CCBN吗？")
			.setPositiveButton("确定", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {   
					//HomeActivity.this.stopService(serviceIntent);  
					android.os.Process.killProcess(android.os.Process.myPid());   
					finish();       
				}
			})
			.setNegativeButton("取消", new OnClickListener() {
				  
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			}).show();
			return true;
		}else{
			return super.dispatchKeyEvent(event);
		}
	}
}
