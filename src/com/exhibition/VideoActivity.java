package com.exhibition;



import java.io.File; 
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;  
import com.exhibition.utils.VideoView;  
import android.app.Activity;  
import android.os.Bundle;
import android.os.Environment;  
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView; 


public class VideoActivity extends Activity implements ActivityInterface {
	private TextView tvTitle;
	private Button btHome;
	private VideoView videoView;
	private static int previousPosition = 0;
	private PopupWindow popupWindow;
	private View viewController;
	private File file;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		file = new File(Environment.getExternalStorageDirectory(),"video.mp4") ;
		findView();               
		addAction();         
	}       
	@Override
	public void findView(){
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);  
		videoView = (VideoView) this.findViewById(R.id.activity_video_sv);
		viewController = getLayoutInflater().inflate(R.layout.video_controller, null); 
		videoView.requestFocus();
	}    
	@Override
	public void addAction() {
		tvTitle.setText(getIntent().getStringExtra("title"));  
		btHome.setOnClickListener(new HomeClickListener(VideoActivity.this));
		videoView.stopPlayback();  
		videoView.setVideoPath(file.getAbsolutePath());
		videoView.seekTo(previousPosition);   
		videoView.start();  
		/*videoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupWindow = new PopupWindow(viewController, 
						  getWindowManager().getDefaultDisplay().getWidth(),
						  getWindowManager().getDefaultDisplay().getHeight());
				popupWindow.showAtLocation(videoView, Gravity.BOTTOM, 0, 0);
			}
		});*/
	} 
	
	@Override
	protected void onStop() {
		super.onStop(); 
		if(videoView.isPlaying()){
			previousPosition = videoView.getCurrentPosition();
		}else{
			previousPosition = 0;
		}
	}
	
	public void video(View view){
		int id = view.getId();
		switch(id){
		case R.id.video_controller_imagebutton_play:
			videoView.stopPlayback();  
			videoView.setVideoPath(file.getAbsolutePath());
			videoView.seekTo(previousPosition);   
			videoView.start();  
			break;
		case R.id.video_controller_imagebutton_pause:
			videoView.pause();
			break;
		}
	}
}
