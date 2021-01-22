package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.utilPojo.SendRequest;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:52
 * @description: 寄件 Send 的Service 层接口
 */
public interface SendService {

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: User寄件下单
	 * @Param: [request]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> sendPack(SendRequest request);
	
	/**
	 * @Description: User支付寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, String> sendPay(String id, String token);

	/**
	 * @Description: Admin确认寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> sendConfirm(String id, String token);

	/**
	 * @Description: Admin发出寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> sendOut(String id, String token);

	/**
	 * @Description: User取消寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> sendCancel(String id, String token);

	/**
	 * @Description: 分页方式获取User寄件集合
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getSendByUser(int currentPage, int pageSize, String token);

	/**
	 * @Description: 获取User寄件数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getTotalByUser(String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页方式获取Admin寄件集合
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getSendByAdmin(int currentPage, int pageSize, String token);

	/**
	 * @Description: 获取Admin寄件数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getTotalByAdmin(String token);

}
