package com.exhibition;



import java.io.File;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VideoActivity extends Activity implements ActivityInterface {
	private TextView tvTitle;
	private Button btHome;
	private SurfaceView surfaceView;
	private MediaPlayer mediaPlayer;
	private boolean pause;
    private int position;
    private String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video);
		mediaPlayer = new MediaPlayer();
		findView();
		addAction(); 
		//把输送给surfaceView的视频画面，直接显示到屏幕上,不要维持它自身的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        start();
	}
	private final class SurfaceCallback implements Callback{
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}
		public void surfaceCreated(SurfaceHolder holder) {
			if(position>0 && path!=null){
				play(position);
				position = 0;
			}
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
			if(mediaPlayer.isPlaying()){
				position = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}
    }
	@Override
	public void findView() {
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
		surfaceView = (SurfaceView) this.findViewById(R.id.activity_video_sv);
	}
	@Override
	public void addAction() {
		tvTitle.setText(getIntent().getStringExtra("title"));
		btHome.setOnClickListener(new HomeClickListener(VideoActivity.this));
		
	}
	private void play(int position) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.prepare();//缓冲
			mediaPlayer.setOnPreparedListener(new PrepareListener(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private final class PrepareListener implements OnPreparedListener{
		private int position;
		
		public PrepareListener(int position) {
		     this.position = position;
		}

		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start();//播放视频
			if(position>0) mediaPlayer.seekTo(position);
		}
	}
	private void start(){
		String filename = "video.mp4";
		if(filename.startsWith("http")){
			path = filename;
			play(0);
		}else{
			File file = new File(Environment.getExternalStorageDirectory(), filename);
			if(file.exists()){
				path = file.getAbsolutePath();
				play(0);
			}else{
				path = null;
				Toast.makeText(this, "出错了", 1).show();
			}
		}
	}
}
