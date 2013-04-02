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
	 * 得到分页商品数据
	 * 
	 * @param type
	 * @param imei
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public String getCommodityList(String type, String imei, String pageNo,
			String pageSize) throws Exception;

	/**
	 * 得到商品总数据
	 * 
	 * @param type
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public String getCommodityTotal(String pid,String vid) throws Exception;

	/**
	 * 得到商品详细
	 * 
	 * @param type
	 * @param id
	 * @param imei
	 * @return
	 * @throws Exception
	 */
	public String getCommodityDetail(String type, String id, String imei)
			throws Exception;

	/**
	 * type 123 imei 手机IMEI号 commodityId 商品ID channel 渠道号 address 地址 consignee
	 * 收货人 phone 联系电话 number 订购数量 price 单价
	 * 
	 * @return
	 * @throws Exception
	 */
	public String submitOrder(String type, String imei, String commodityId,
			String channel, String address, String phone, String number,
			String price) throws Exception;
	/**
	 * 注册serviceToken
	 * @return
	 * @throws Exception
	 */
	public String registService(String serviceToken, String exhibitionCode, String mobilePlatform) throws Exception;
	
	
}
