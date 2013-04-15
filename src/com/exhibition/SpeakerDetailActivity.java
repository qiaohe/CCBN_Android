package com.exhibition;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;  
import com.exhibition.conts.StringPools;  
import com.exhibition.entities.EventData.Speaker;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.utils.DataUtil;
import com.exhibition.utils.ImageURLUtil;

public class SpeakerDetailActivity extends Activity implements
		ActivityInterface {
	private TextView mTitle;
	private ImageView mSpeakerPhoto;
	private TextView mSpeakerName, mSpeakerCompany, mSpeakerPosition,
			mSpeakerContent;
	private Button mHomeBtn;
	private String titleStr = "";
	private Speaker mSpeaker;
	private List<Speaker> mSpeakers = new ArrayList<Speaker>();
	private String speakerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.speaker_detail_page);
		initData();
		findView();
		addAction();
	}

	private void initData() {
		titleStr = getIntent().getStringExtra("title");
		speakerName = getIntent().getStringExtra("speaker");
		mSpeakers = DataUtil.getSpeakers(this);
		for (int i = 0; i < mSpeakers.size(); i++) {
			if (speakerName.equals(mSpeakers.get(i).getName())) {
				mSpeaker = mSpeakers.get(i);
			}
		}
	}

	@Override
	public void findView() {
		mTitle = (TextView) findViewById(R.id.title_text);
		mSpeakerPhoto = (ImageView) findViewById(R.id.speakeritem_photo);
		mSpeakerName = (TextView) findViewById(R.id.speaker_names);
		mSpeakerCompany = (TextView) findViewById(R.id.speaker_company);
		mSpeakerPosition = (TextView) findViewById(R.id.speaker_position);
		mSpeakerContent = (TextView) findViewById(R.id.speaker_content);
		mHomeBtn = (Button) findViewById(R.id.home_button);
	}

	@Override
	public void addAction() {
		mTitle.setText(titleStr);
		if (mSpeaker.getPhoto().equals("")) {
			mSpeakerPhoto.setImageResource(R.drawable.default_avatar);
		} else {
			ImageURLUtil.loadImage(
					StringPools.mClientPICURL + mSpeaker.getPhoto(),
					mSpeakerPhoto);
		}
		mSpeakerName.setText(mSpeaker.getName());
		mSpeakerCompany.setText(mSpeaker.getCompany());
		mSpeakerPosition.setText(mSpeaker.getPosition());
		mSpeakerContent.setText(mSpeaker.getProfile());

		mHomeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goToHomePage();
			}

		});
	}

	private void goToHomePage() {
		Intent it = new Intent(SpeakerDetailActivity.this, HomeActivity.class);
		startActivity(it);
		finish();
	}

}
