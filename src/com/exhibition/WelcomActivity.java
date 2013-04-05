package com.exhibition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.exhibition.conts.StringPools;
import com.exhibition.db.XmlDB;
import com.exhibition.entities.CheckInData;
import com.exhibition.entities.EventData;
import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.service.ClientController;
import com.exhibition.utils.DataUtil;
import com.google.gson.Gson;

public class WelcomActivity extends Activity implements ActivityInterface {
	private EventData mEventDataOld;   //本地的数据
	private EventData mEventDataNew;   //新的网络数据
	private TextView dateChTextView, dateEnTextView;
	private TextView adsChTextView, adsEnTextView;
	private Context context;
	private ImageView mLogo;
	private AnimationDrawable mAnimationDrawable;
	private ClientController controller;
	private String mJsonData;
	private DemoApplication app;
	private TelephonyManager phoneManager;
	private boolean flag = true;
	private LocationData locData = new LocationData();
	private LocationClient mLocationClient;
	private BMapManager mBMapManagerNew;
	private String addressStr;
	private CheckInData mCheckInData = new CheckInData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcom_page);

		context = this.getApplicationContext();
		mEventDataOld = DataUtil.getEventData(context);
		controller = ClientController.getController(this);
		findView();
		
		mLogo.setBackgroundResource(R.anim.logo_anim);
		//AnimationDrawable逐帧动画类
		mAnimationDrawable = (AnimationDrawable) mLogo.getBackground();
		mLogo.post(new Runnable() {

			public void run() {
				mAnimationDrawable.start();
			}
		});
		
		DemoApplication app = (DemoApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(DemoApplication.strKey,
					new DemoApplication.MyGeneralListener());
		}
		
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(new MyLocationListenner());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();

		
		new Thread() {
			public void run() {
				try {
					mEventDataNew = new Gson().fromJson(
							XmlDB.getInstance(WelcomActivity.this)
									.getKeyStringValue(
											StringPools.CCBN_ALL_DATA, ""),
							EventData.class);
					if (mEventDataNew != null
							|| !mEventDataOld.getUpdatedAt().equals(
									mEventDataNew.getUpdatedAt())) {
						mJsonData = controller.getService().findAll();

						XmlDB.getInstance(WelcomActivity.this).saveKey(
								StringPools.CCBN_ALL_DATA, mJsonData);
					}   
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

	}

	@Override
	public void findView() {
		mLogo = (ImageView) findViewById(R.id.logo);
	}

	@Override
	public void addAction() {

	}

	private void goToNextPage() {
		Intent it = new Intent(WelcomActivity.this, HomeActivity.class);
		it.putExtra("latitude", mCheckInData.latitude);
		it.putExtra("longitude", mCheckInData.longitude);
		it.putExtra("address", mCheckInData.address);
		startActivity(it);
		finish();
	}
	private void getAddress() {
		MKSearch mMKSearch = new MKSearch();
		mBMapManagerNew = new BMapManager(this);
		mBMapManagerNew.init(StringPools.baiDuMapStrKey, null);
		mMKSearch.init(mBMapManagerNew, new MySearchListener());
		mMKSearch.reverseGeocode(new GeoPoint((int) (locData.latitude * 1E6),
				(int) (locData.longitude * 1E6)));
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			mCheckInData.latitude = location.getLatitude();
			mCheckInData.longitude = location.getLongitude();
			locData.direction = 2.0f;
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			getAddress();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	/**
	 * 内部类实现MKSearchListener接口,用于实现异步搜索服务
	 * 
	 * @author liufeng
	 */
	public class MySearchListener implements MKSearchListener {
		/**
		 * 根据经纬度搜索地址信息结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {
			if (result == null) {
				return;
			}
			StringBuffer sb = new StringBuffer();
			// 经纬度所对应的位置
			sb.append(result.strAddr).append("/n");
			addressStr = sb.toString();
			mCheckInData.address = addressStr;
			System.out.println(addressStr);
			goToNextPage();
		}

		/**
		 * 驾车路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
		}

		/**
		 * POI搜索结果（范围检索、城市POI检索、周边检索）
		 * 
		 * @param result
		 *            搜索结果
		 * @param type
		 *            返回结果类型（11,12,21:poi列表 7:城市列表）
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
		}

		/**
		 * 公交换乘路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
		}

		/**
		 * 步行路线搜索结果
		 * 
		 * @param result
		 *            搜索结果
		 * @param iError
		 *            错误号（0表示正确返回）
		 */
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {

		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {

		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {

		}
	}
}
