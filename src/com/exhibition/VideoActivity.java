package com.exhibition;



import java.io.File;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends Activity implements ActivityInterface {
	private TextView tvTitle;
	private Button btHome;
	private VideoView videoView;
	private static int previousPosition = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video); 
		findView();
		addAction(); 
	}
	@Override
	public void findView() {
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
		videoView = (VideoView) this.findViewById(R.id.activity_video_sv);
	}
	@Override
	public void addAction() {
		tvTitle.setText(getIntent().getStringExtra("title"));
		btHome.setOnClickListener(new HomeClickListener(VideoActivity.this));
		File file = new File(Environment.getExternalStorageDirectory(),"video.mp4") ;
		videoView.setVideoPath(file.getAbsolutePath());
		videoView.seekTo(previousPosition);
		videoView.start(); 
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
}
