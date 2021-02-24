package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.utilPojo.SendRequest;

import java.util.Map;

/**
 * author: 严晨
 * date: 2021/1/4
 * time: 15:52
 * 寄件 Send 的 Service 层接口
 */
public interface SendService {

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * 流程相关
	 **/

	/**
	 * User 寄件下单
	 * @param request   寄件请求实体
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> sendPack(SendRequest request);
	
	/**
	 * User 支付寄件
	 * @param id    学号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 */
	Map<String, String> sendPay(String id, String token);

	/**
	 * Admin 确认寄件
	 * @param id    编号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 */
	Map<String, String> sendConfirm(String id, String token);

	/**
	 * Admin 发出寄件
	 * @param id    编号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 */
	Map<String, String> sendOut(String id, String token);

	/**
	 * User 取消寄件
	 * @param id    学号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 */
	Map<String, String> sendCancel(String id, String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 **/

	/**
	 * 分页方式获取 User 寄件集合
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @param org           快递公司
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getSendByUser(int currentPage, int pageSize, String token, String org);

	/**
	 * 获取 User 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getTotalByUser(String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 **/

	/**
	 * 分页方式获取 Admin 寄件集合
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getSendByAdmin(int currentPage, int pageSize, String token);

	/**
	 * 获取 Admin 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getTotalByAdmin(String token);

}
