package com.exhibition.service;

public interface ClientService {
    /**
     * 初始化App信息
     *
     * @return
     * @throws Exception
     */
    public String findAll() throws Exception;

    /**
     * 签到
     *
     * @param name
     * @param version
     * @throws Exception
     */
    public void checkIn(String serviceToken, String exhibitionCode, double latitude,
                        double longitude, String address) throws Exception;

    /**
     * 注册serviceToken
     *
     * @return
     * @throws Exception
     */
    public String registerService(String serviceToken, String exhibitionCode, String mobilePlatform) throws Exception;


}
