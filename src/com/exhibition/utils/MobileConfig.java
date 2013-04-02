package com.exhibition.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

import com.exhibition.conts.StringPools;

public class MobileConfig {

    public enum ScreenType {
        xlarge, large, middle, small;
    }

    private TelephonyManager tm;
    private Context mCon;
    private ConnectivityManager cm;

    private static MobileConfig mMobileConfig;
    private PackageInfo mPkgInfo;

    private static final float HDPI = 1.5f;

    public static MobileConfig getMobileConfig(Context mCon) {
        if (mMobileConfig == null) {
            mMobileConfig = new MobileConfig(mCon);
        }
        return mMobileConfig;
    }

    public MobileConfig(Context mCon) {
        this.mCon = mCon;
        init();
        PackageManager manager = mCon.getPackageManager();
        try {
            mPkgInfo = manager.getPackageInfo(mCon.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    void init() {
        tm = (TelephonyManager) mCon.getSystemService(Context.TELEPHONY_SERVICE);
        cm = (ConnectivityManager) mCon
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public float getDensity() {
        if (mCon != null) {
            return mCon.getResources().getDisplayMetrics().density;
        } else {
            return HDPI;
        }
    }

    /**
     * 获取包版本Code值. 在AndroidManifest.xml中的versionCode中进行配置.
     */
    public int getPkgVerCode() {
        return mPkgInfo.versionCode;
    }

    /**
     * 返回应用包名.
     */
    public String getPackageName() {
        return mPkgInfo.packageName;
    }

    public String getLastDeviceId() {
        return tm.getDeviceId();
    }
    
	/**
	 * 1.获取设备ID,
	 * <p>
	 * 2.如果获取不到,在尝试获取手机MAC地址,
	 * <p>
	 * 3.如果在获取不到,则获取开机第一次生成的Android ID,
	 * <p>
	 * 4.在获取不成功,则随即生成一个唯一标识串.
	 */
	public String getDeviceId() {
		if (getLastDeviceId() == null) {
			if (getLocalMacAddress() == null) {
				String mAndroidId = android.provider.Settings.System.getString(
						mCon.getContentResolver(), "android_id");
				if (mAndroidId == null) {
					SharedPreferences mEverSetting = mCon.getSharedPreferences(
							StringPools.PermanentSetting, 0);
					if (mEverSetting.getString(StringPools.mDeviceIdKey, null) == null) {
						mEverSetting
								.edit()
								.putString(
										StringPools.mDeviceIdKey,
										MD5.getMD5(System.currentTimeMillis()
												+ Math.random() + "")).commit();
					}
					return mEverSetting.getString(StringPools.mDeviceIdKey,
							null);
				}
				return mAndroidId;
			}
			return getLocalMacAddress();
		}
		return getLastDeviceId();
	}

    public String getLine1Number() {
        return tm.getLine1Number();
    }

    public String getSimSerialNumber() {
        return tm.getSimSerialNumber();
    }

    public String getSubscriberId() {
        return tm.getSubscriberId();
    }

    public String getNetworkOperator() {
        return tm.getNetworkOperator();
    }

    public String getNetworkOperatorName() {
        return tm.getNetworkOperatorName();
    }

    public int getNetworkType() {
        return tm.getNetworkType();
    }

    public String getSimOperatorName() {
        return tm.getSimOperatorName();
    }

    public int getPhoneType() {
        return tm.getPhoneType();
    }

    public String getModel() {
        return Build.MODEL;
    }

    public String getBrand() {
        return Build.BRAND;
    }

    public String getDevice() {
        return Build.DEVICE;
    }

    public String getProduct() {
        return Build.PRODUCT;
    }

    public String getMANUFACTURER() {
        return Build.MANUFACTURER;
    }

    public String getOS() {
        return "android";
    }

    /** 返回屏幕宽度(px)*/
    public int getWidth() {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        return dm.widthPixels;
    }

    public int getHeight() {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        return dm.heightPixels;
    }

    public int getXByScale(int pX) {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        float scale = (dm.widthPixels / 320f) < (dm.heightPixels / 480f) ? dm.widthPixels / 320f : dm.heightPixels / 480f;
        return (int) (pX * scale);
    }

    public int getYByScale(int pY) {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        float scale = (dm.widthPixels / 320f) < (dm.heightPixels / 480f) ? dm.widthPixels / 320f : dm.heightPixels / 480f;
        return (int) (pY * scale);
    }

    public float getScale() {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        float scale = (dm.widthPixels / 320f) < (dm.heightPixels / 480f) ? dm.widthPixels / 320f : dm.heightPixels / 480f;
        return scale;
    }

    public int getYByScaleHeight(int pY) {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        float scale = (dm.heightPixels / 480f);
        return (int) (pY * scale);
    }

    public int getXByScaleWidth(int pX) {
        DisplayMetrics dm = mCon.getApplicationContext().getResources()
                .getDisplayMetrics();
        float scale = (dm.widthPixels / 320f);
        return (int) (pX * scale);
    }

    public String getUuid() {
        return Settings.Secure.getString(this.mCon.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    public String getNetworkTypeName() {
        String typeName = "others";
        if (cm == null) {
            typeName = "others";
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                typeName = "others";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {

                switch (this.getNetworkType()) {
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        typeName = "EDGE";
                        break;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        typeName = "GPRS";
                        break;

                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        typeName = "UMTS";
                        break;

                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        typeName = "others";
                        break;
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                typeName = "WIFI";
            }
        }
        return typeName;
    }

    /**
     * Get the model of phone.For example:C8600,W711 and so on.
     *
     * @return
     */
    public String getMobileModel() {
        String mPhoneType = Build.MODEL;
        return mPhoneType;
    }

    /**
     * Get the operation system of phone.
     */
    public String getMobileOsVersion() {
        String mSdkVersion = Build.VERSION.RELEASE;
        return mSdkVersion;
    }

    ;

    public String getPkgVerName() {
        return mPkgInfo.versionName;
    }

    /**
     * 获取手机屏幕的类型
     *
     * @param activity android activity
     * @return 手机屏幕的类型
     */
    public static ScreenType getScreenType(Activity activity) {
        DisplayMetrics metrics = getDisplayMetrics(activity);

//        LogDebugger.debug("MobileConfig", "width: " + metrics.widthPixels + " , height: " + metrics.heightPixels);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        if (width > height) {
            height = metrics.widthPixels;
            width = metrics.heightPixels;
        }

        return getScreenType(height, width);

    }

    /** 获取手机屏幕属于何种尺寸. */
    public static ScreenType getScreenType(int height, int width) {
        if (width >= 720 && height >= 960) {
            return ScreenType.xlarge;
        } else if (width >= 480 && height >= 640) {
            return ScreenType.large;
        } else if (width >= 320 && height >= 470) {
            return ScreenType.middle;
        } else {
            return ScreenType.small;
        }
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @param activity android activity
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }
    
    /** 获取手机mac地址。*/
    public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) mCon.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

}
