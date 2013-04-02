package com.exhibition.db;

import android.content.Context;
import android.content.SharedPreferences;

/** The class is used for saving simple key-value in a xml file. */
public class XmlDB {

    private Context context;
    /**
     * Preferences Name that we use.
     */
    public static final String Pref_Name = "jjdd_config";

    /**
     * Holds the single instance that is shared by the process.
     */
    private static XmlDB sInstance = null;

    /**
     * Return the single SharedPreferences instance.
     */
    public static XmlDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new XmlDB(context);
        }
        return sInstance;
    }

    public XmlDB(Context context) {
        this.context = context;
    }

    /**
     * Save the String-String key-values in sharePreference file.
     */
    public void saveKey(String mKey, String mValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        SharedPreferences.Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.putString(mKey, mValue);
            editor.commit();
        }
    }

    public void saveKey(String mKey, String mValue, String prefName) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.putString(mKey, mValue);
            editor.commit();
        }
    }

    public String getKeyStringValue(String mKey, String mDefValue) {
        String mStr = null;
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        if (mSharePrefs != null) {
            mStr = mSharePrefs.getString(mKey, mDefValue);
        }
        return mStr;
    }

    public String getKeyStringValue(String mKey, String mDefValue, String prefName) {
        String mStr = null;
        SharedPreferences mSharePrefs = context.getSharedPreferences(prefName, 0);
        if (mSharePrefs != null) {
            mStr = mSharePrefs.getString(mKey, mDefValue);
        }
        return mStr;
    }

    public int getKeyIntValue(String mKey, int mDefValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        int mInt = 0;
        if (mSharePrefs != null) {
            mInt = mSharePrefs.getInt(mKey, mDefValue);
        }
        return mInt;
    }

    public boolean getKeyBooleanValue(String mKey, boolean mDefValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        boolean mBool = false;
        if (mSharePrefs != null) {
            mBool = mSharePrefs.getBoolean(mKey, mDefValue);
        }
        return mBool;
    }

    public Float getKeyFloatValue(String mKey, int mDefValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        Float mFloat = null;
        if (mSharePrefs != null) {
            mFloat = mSharePrefs.getFloat(mKey, mDefValue);
        }
        return mFloat;
    }

    /**
     * 保存整型的键值对到配置文件当中.
     */
    public void saveKey(String mKey, int mValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        SharedPreferences.Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.putInt(mKey, mValue);
            editor.commit();
        }
    }

    /**
     * 保存boolean类型的键值对到配置文件当中.
     */
    public void saveKey(String mKey, boolean mValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        SharedPreferences.Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.putBoolean(mKey, mValue);
            editor.commit();
        }
    }

    /**
     * 保存float类型的键值对到配置文件当中.
     */
    public void saveKey(String mKey, Float mValue) {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        SharedPreferences.Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.putFloat(mKey, mValue);
            editor.commit();
        }
    }

    public void clear() {
        SharedPreferences mSharePrefs = context.getSharedPreferences(Pref_Name, 0);
        SharedPreferences.Editor editor;
        if (mSharePrefs != null) {
            editor = mSharePrefs.edit();
            editor.clear();
            editor.commit();
        }
    }

	public void clear(String PrefName) {
		SharedPreferences mSharePrefs = context.getSharedPreferences(PrefName,
				0);
		SharedPreferences.Editor editor;
		if (mSharePrefs != null) {
			editor = mSharePrefs.edit();
			editor.clear();
			editor.commit();
		}
	}

}