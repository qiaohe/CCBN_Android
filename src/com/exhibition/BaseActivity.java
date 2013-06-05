package com.exhibition;

import com.exhibition.interfaces.ActivityInterface;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity implements ActivityInterface,OnClickListener{
	/**
	 * 根布局对象
	 */
	protected LinearLayout root;
	/**
	 * 标题栏左边按钮
	 */
	protected Button titlebarLeftButton;
	/**
	 * 标题栏中间文字
	 */
	protected TextView titlebarText;
	/**
	 * 标题栏右边按钮
	 */
	protected Button titlebarRightButton;
	
	private ProgressDialog progressDialog;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base_layout);
		findView();
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void findView() {
		root = (LinearLayout) findViewById(R.id.root);
		titlebarLeftButton = (Button) findViewById(R.id.left_button);
		titlebarRightButton = (Button) findViewById(R.id.right_button);
		titlebarText = (TextView) findViewById(R.id.title_text);
	}
	
	@Override
	public void addAction() {
		titlebarLeftButton.setOnClickListener(this);
	}
	/**
	 * 显示等待框
	 */
	protected void showProgress() {
		showProgress("Please wait", "progressing");
	}

	/**
	 * 显示等待框
	 * 
	 * @param title
	 * @param message
	 */
	protected void showProgress(String title, String message) {
		progressDialog = ProgressDialog.show(this, title, message);
	}

	/**
	 * 取消等待框
	 */
	protected void dismissProgress() {
		if (progressDialog != null) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {

			}
		}
	}

	/**
	 * 显示Toast提示
	 * 
	 * @param message
	 */
	protected void showTip(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 不销毁ProgressDialog会出现view not attached to window manager异常
		dismissProgress();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
}
