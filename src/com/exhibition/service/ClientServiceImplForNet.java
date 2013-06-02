package com.exhibition.service;

import android.widget.Toast;
import com.exhibition.AppConfig;
import com.exhibition.netty.client.ClientServiceToken;
import com.exhibition.utils.CustomerHttpClient;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ClientServiceImplForNet implements ClientService {
    private ClientController controller;

    public ClientServiceImplForNet() {

    }

    public ClientServiceImplForNet(ClientContext context) {
        super();
    }

    public String getBusinessData(String name, String version) throws Exception {
        return version;
    }

    /**
     * 初始化App信息
     */
    public String findAll() throws Exception {
        try {
            String response = CustomerHttpClient.get(AppConfig.URL_ALL_DATA, "");
            return response;
        } catch (Exception ignore) {
        }
        return null;
    }

    /**
     * 签到
     */
    @Override
    public void checkIn(String serviceToken, String exhibitionCode, double latitude,
                        double longitude, String address) throws Exception {
        // 准备数据
        NameValuePair param1 = new BasicNameValuePair("serviceToken", serviceToken);
        NameValuePair param2 = new BasicNameValuePair("exhibitionCode", exhibitionCode);
        NameValuePair param3 = new BasicNameValuePair("latitude", latitude + "");
        NameValuePair param4 = new BasicNameValuePair("longitude", longitude + "");
        NameValuePair param5 = new BasicNameValuePair("address", address);

        try {
            System.out.println(param1 + ":" + param2 + ":" + param3 + ":" + param4 + ":" + param5);
            // 使用工具类直接发出POST请求
            String response = CustomerHttpClient.post(AppConfig.URL_CHECK_IN, param1, param2,
                    param3, param4, param5);

        } catch (RuntimeException e) {
            // 请求失败
            e.getMessage();
        } catch (Exception e) {

        }
    }

    public String registerService(String serviceToken, String exhibitionCode,
                                  String mobilePlatform) throws Exception {
        ClientServiceToken mClientServiceToken = new ClientServiceToken();
        mClientServiceToken.setServiceToken(serviceToken);
        mClientServiceToken.setExhibitionCode(exhibitionCode);
        mClientServiceToken.setMobilePlatform(mobilePlatform);
        String jsonData = new Gson().toJson(mClientServiceToken);  
        try {
            String response = CustomerHttpClient.postJson(AppConfig.URL_REGISTER, jsonData);
            return response;
        } catch (Exception e) { 
            Toast.makeText( 
                    controller.getCurrentActivity().getApplicationContext(),
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;  
    }
    
    public String getNewsData() throws Exception {  
    	try {
            String response = CustomerHttpClient.get(AppConfig.URL_NEWS, "");
            return response;
        } catch (Exception ignore) {  
        }
        return null;
    }  
}
