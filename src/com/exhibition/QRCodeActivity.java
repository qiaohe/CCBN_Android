package com.exhibition;

import com.exhibition.interfaces.ActivityInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class QRCodeActivity extends Activity implements ActivityInterface {
	private EditText dtUsername;
	private EditText dtPassword;
	private Button btHome;
	private Button btLogin;
	private ImageView ivQrcode;
	private String username;
	private String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qrcode);
		findView();
		initData();
		addAction(); 
	}
	
	public void initData(){
		username = dtUsername.getText().toString();
		password = dtPassword.getText().toString();
	}
	
	@Override
	public void findView() {
		dtUsername = (EditText) this.findViewById(R.id.activity_qrcode_et_username);
		dtPassword = (EditText) this.findViewById(R.id.activity_qrcode_et_password);
		btHome = (Button) this.findViewById(R.id.home_button_second);
		btLogin = (Button) this.findViewById(R.id.activity_qrcode_bt_login);
		ivQrcode = (ImageView) this.findViewById(R.id.activity_qrcode_iv_qrcode);
	}
	
	@Override
	public void addAction() {
		btLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
	}
	
}
