package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.utilPojo.SendRequest;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:52
 * @description:
 */
public interface SendService {

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: User寄件下单
	 * @Param: [request, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> sendPack(SendRequest request, String token);

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
