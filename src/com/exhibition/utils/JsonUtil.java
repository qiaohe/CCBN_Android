package com.exhibition.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * json util class ,use in for analyzing json
 * 
 * @author Arvin_Ho
 * 
 */
public class JsonUtil {
	private static final String TAG = "jsonUtil";
	private JSONObject jsonObject;

	private JsonUtil(String json) {
		Log.e(TAG, "json=" + json);
		jsonObject = getJsonObject(json);
		if (jsonObject == null) {
			Log.e(TAG, "jsonobject is null");
		}
	}

	public JsonUtil() {
		super();
	}

	public static JsonUtil newJsonUtil(String json) {
		JsonUtil util = new JsonUtil(json);
		return util;
	}

	/**
	 * get json object
	 * 
	 * @param json
	 *            json data
	 * @return JOSNObject
	 */
	public JSONObject getJsonObject(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			Log.e(TAG, "create jsonobject exception");
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * get String data
	 * 
	 * @param json
	 *            json data
	 * @param key
	 *            param
	 * @return String data
	 * @throws JSONException
	 */
	public String getString(String key) throws JSONException {
		if (jsonObject != null) {
			return jsonObject.getString(key);
		} else {
			return null;
		}

	}

	/**
	 * get String data
	 * 
	 * @param json
	 *            json data
	 * @param key
	 *            param
	 * @return int data
	 * @throws JSONException
	 */
	public int getInt(String key) throws JSONException {
		if (jsonObject != null) {
			return jsonObject.getInt(key);
		} else {
			return -1;
		}

	}

	/**
	 * get Double data
	 * 
	 * @param json
	 *            json data
	 * @param key
	 *            param
	 * @return double data
	 * @throws JSONException
	 */
	public double getDouble(String key) throws JSONException {
		if (jsonObject != null) {
			return jsonObject.getDouble(key);
		} else {
			return -1;
		}

	}

	/**
	 * This Method use in jsonObject get current class with object
	 * 
	 * @param jsonObject
	 * @param c
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(Class<?> c) throws Exception {
		if (jsonObject != null) {
			return getObject(c.getSimpleName().toLowerCase(), c);
		} else {
			return null;
		}
	}

	/**
	 * This Method use in jsonObject get current class with object
	 * 
	 * @param jsonObject
	 * @param key
	 *            query key
	 * @param c
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(String key, Class<?> c) throws Exception {
		if (jsonObject != null) {
			return getObject(jsonObject, key, c);
		} else {
			return null;
		}
	}

	public Object getObject(JSONObject jsonObject, Class<?> c) throws Exception {
		return getObject(jsonObject, c.getSimpleName().toLowerCase(), c);
	}

	/**
	 * This Method use in jsonObject get current class with object
	 * 
	 * @param jsonObject
	 * @param key
	 *            query key
	 * @param c
	 *            class
	 * @return object
	 * @throws Exception
	 */
	public Object getObject(JSONObject jsonObject, String key, Class<?> c)
			throws Exception {
		Log.e(TAG, "key ==  " + key);
		Object bean = null;

		if (jsonObject != null) {
			JSONObject jo = null;
			if (key != null) {
				jo = jsonObject.getJSONObject(key);
			} else {
				jo = jsonObject;
			}
			if (jo != null) {
				if (c.equals(null)) {
					Log.e(TAG, "class is null");
					bean = jo.get(key);
				} else {
					bean = c.newInstance();
					Field[] fs = c.getDeclaredFields();
					for (int i = 0; i < fs.length; i++) {
						Field f = fs[i];
						f.setAccessible(true);
						Type type = f.getGenericType();
						String value = jo.getString(f.getName());
						Log.e(TAG, f.getName() + "=" + value);
						if (type.equals(int.class)) {
							f.setInt(bean, Integer.valueOf(value));
						} else if (type.equals(double.class)) {
							f.setDouble(bean, Double.valueOf(value));
						} else {
							f.set(bean, value);
						}
					}
				}
			} else {
				Log.e(TAG, "in jsonobject not key ");
			}
		} else {
			Log.e(TAG, "current param jsonobject is null");
		}
		return bean;
	}

	/**
	 * This method use in jsonObject get list object
	 * 
	 * @param key
	 *            list key
	 * @param objectKey
	 *            object key
	 * @param c
	 *            object
	 * @return list
	 * @throws Exception
	 */
	public List<Object> getList(String key, Class<?> c) throws Exception {
		List<Object> list = null;
		if (jsonObject != null) {
			JSONArray jsonArray = jsonObject.getJSONArray(key);
			if (!jsonArray.isNull(0)) {
				list = new ArrayList<Object>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsObject = jsonArray.getJSONObject(i);
					Object object = getObject(jsObject, null, c);
					list.add(object);
				}
			}
		}
		return list;
	}

	/**
	 * Test class field value
	 * 
	 * @param c
	 * @param classObject
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String getFieldValue(Class<?> c, Object classObject)
			throws IllegalArgumentException, IllegalAccessException {
		StringBuffer sb = new StringBuffer();
		Field[] fs = c.getFields();
		for (int i = 0; i < fs.length; i++) {
			String s = fs[i].getName() + "=" + fs[i].get(classObject);
			sb.append(s).append("\n");
		}
		// Log.e(TAG, sb.toString());
		return sb.toString();
	}
}
