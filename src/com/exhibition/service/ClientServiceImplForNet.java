package com.exhibition.service;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.Toast;

import com.exhibition.netty.client.ClientServiceToken;
import com.exhibition.utils.CustomerHttpClient;
import com.google.gson.Gson;

public class ClientServiceImplForNet implements ClientService {
	private ClientController controller;
	private String serverIP;
	private int serverPort;

	public ClientServiceImplForNet(){
		
	}
	
	public ClientServiceImplForNet(ClientContext context) {
		super();
		// this.context = context;
		serverIP = context.getSystemProperty("ServerIP");
		serverPort = Integer.parseInt(context.getSystemProperty("ServerPort"));
	}

	public String getBusinessData(String name, String version) throws Exception {
		return version;
	}

	/**
	 * 初始化App信息
	 */
	public String findAll() throws Exception {
		final String url = "http://" + serverIP + ":" + serverPort
				+ "/exhibition/api/exhibitions/CCBN";
		try {
			// 使用工具类直接发出GET请求
			String response = CustomerHttpClient.get(url,"");
			return response;
		} catch (RuntimeException e) {
			// 请求失败或�1�7�连接失贄1�7
		} catch (Exception e) {

		}
		return null;
	}
	/**
	 * 签到
	 */
	@Override
	public void checkIn(String serviceToken, String exhibitionCode, double latitude,
			double longitude, String address) throws Exception {
		final String url = "http://" + serverIP + ":" + serverPort
		+ "/exhibition/api/attendees/check_in";
		// 准备数据
		NameValuePair param1 = new BasicNameValuePair("serviceToken", serviceToken);
		NameValuePair param2 = new BasicNameValuePair("exhibitionCode", exhibitionCode);
		NameValuePair param3 = new BasicNameValuePair("latitude", latitude+"");
		NameValuePair param4 = new BasicNameValuePair("longitude", longitude+""); 
		NameValuePair param5 = new BasicNameValuePair("address", address);
		
		try {
			System.out.println(param1+":"+param2+":"+param3+":"+param4+":"+param5);
			// 使用工具类直接发出POST请求
			String response = CustomerHttpClient.post(url, param1, param2,
					param3, param4,param5);
			
		} catch (RuntimeException e) {
			// 请求失败
			e.getMessage();
		} catch (Exception e) {

		}
	}

	/**
	 * 总商品列表
	 */
	@Override
	public String getCommodityTotal(String pid,String vid) throws Exception {
		final String url = "http://data.f139.com/list.do";
		// 准备数据
		NameValuePair param1 = new BasicNameValuePair("pid", pid);
		NameValuePair param2 = new BasicNameValuePair("vid", vid);
		try {
			// 使用工具类直接发出POST请求
			String response = CustomerHttpClient.post(url, param1,param2);
			return response;
		} catch (RuntimeException e) {
			// 请求失败或�1�7�连接失贄1�7
			Toast.makeText(
					controller.getCurrentActivity().getApplicationContext(),
					e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 提交订单
	 */
	@Override
	public String submitOrder(String type, String imei, String commodityId,
			String channel, String address, String phone, String number,
			String price) throws Exception {
		final String url = "http://" + serverIP + "/server/market";
		// 准备数据
		NameValuePair param1 = new BasicNameValuePair("type", type);
		NameValuePair param2 = new BasicNameValuePair("imei", imei);
		NameValuePair param3 = new BasicNameValuePair("commodityId",
				commodityId);
		NameValuePair param4 = new BasicNameValuePair("channel", channel);
		NameValuePair param5 = new BasicNameValuePair("address", address);
		NameValuePair param6 = new BasicNameValuePair("phone", phone);
		NameValuePair param7 = new BasicNameValuePair("number", number);
		NameValuePair param8 = new BasicNameValuePair("price", price);
		try {
			// 使用工具类直接发出POST请求
			String response = CustomerHttpClient.post(url, param1, param2,
					param3, param4, param5, param6, param7, param8);
			return response;
		} catch (RuntimeException e) {
			Toast.makeText(
					controller.getCurrentActivity().getApplicationContext(),
					e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			
		}
		return null;
	}
	/**
	 * 商品详细
	 */
	@Override
	public String getCommodityDetail(String type, String id, String imei)
			throws Exception {
		final String url = "http://" + serverIP + "/server/market";
		// 准备数据
		NameValuePair param1 = new BasicNameValuePair("type", type);
		NameValuePair param2 = new BasicNameValuePair("id", id);
		NameValuePair param3 = new BasicNameValuePair("imei", imei);
		try {
			// 使用工具类直接发出POST请求
			String response = CustomerHttpClient.post(url, param1,param2,param3);
			return response;
		} catch (RuntimeException e) {
			// 请求失败或�1�7�连接失贄1�7
			Toast.makeText(
					controller.getCurrentActivity().getApplicationContext(),
					e.getMessage(), Toast.LENGTH_SHORT).show();
		} catch (Exception e) {

		}
		return null;
	}
	
	@Override
	public String getCommodityList(String type, String imei, String pageNo,
			String pageSize) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String registService(String serviceToken, String exhibitionCode,
			String mobilePlatform) throws Exception {
		final String url = "http://" + serverIP + "/exhibition/api/attendees";
		ClientServiceToken mClientServiceToken = new ClientServiceToken();
		mClientServiceToken.setServiceToken(serviceToken);
		mClientServiceToken.setExhibitionCode(exhibitionCode);
		mClientServiceToken.setMobilePlatform(mobilePlatform);
		String jsonData = new Gson().toJson(mClientServiceToken);
		try {
			// 使用工具类直接发出POST请求
			String response = CustomerHttpClient.postJson(url,jsonData);
			return response;
		} catch (Exception e) {
			// 请求
			Toast.makeText(
					controller.getCurrentActivity().getApplicationContext(),
					e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		return null;
	}

}
