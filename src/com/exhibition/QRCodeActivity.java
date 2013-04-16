package com.exhibition;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;
import com.exhibition.utils.ImageURLUtil;

public class QRCodeActivity extends Activity implements ActivityInterface {
    private EditText dtUsername;
    private EditText dtPassword;
    private Button btHome;
    private Button btLogin;
    private ImageView ivQrcode;
    private String username;
    private String password;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_qrcode);
        findView();
        username = dtUsername.getText().toString();
        password = dtPassword.getText().toString();
        addAction();
    }

    @Override
    public void findView() {
        dtUsername = (EditText) this.findViewById(R.id.activity_qrcode_et_username);
        dtPassword = (EditText) this.findViewById(R.id.activity_qrcode_et_password);
        btHome = (Button) this.findViewById(R.id.home_button_second);
        btLogin = (Button) this.findViewById(R.id.activity_qrcode_bt_login);
        ivQrcode = (ImageView) this.findViewById(R.id.activity_qrcode_iv_qrcode);
        tvTitle = (TextView) this.findViewById(R.id.title_text_second);
    }

    @Override
    public void addAction() {
        tvTitle.setText(getIntent().getStringExtra("title"));
        btHome.setOnClickListener(new HomeClickListener(QRCodeActivity.this));
        btLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = AppConfig.URL_QRCODE + "?login=" + username + "&password=" + password;
                ImageURLUtil.loadImage(url, ivQrcode);
            }
        });
    }
}
