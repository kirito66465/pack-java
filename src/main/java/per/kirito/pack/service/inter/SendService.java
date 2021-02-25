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
	 * @return java.lang.String
	 */
	String sendPay(String id, String token);

	/**
	 * Admin 确认寄件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	String sendConfirm(String ids, String token);

	/**
	 * Admin 发出寄件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	String sendOut(String ids, String token);

	/**
	 * User 取消寄件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	String sendCancel(String ids, String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 **/

	/**
	 * 分页方式获取 User 寄件集合
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:寄件状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getSendByUser(String json);

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
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, status:寄件状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getSendByAdmin(String json);

	/**
	 * 获取 Admin 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getTotalByAdmin(String token);

}
