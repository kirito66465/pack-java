package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.utilPojo.SendRequest;
import per.kirito.pack.service.inter.SendService;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:51
 * @description: 寄件 Send 的 controller 层
 */
@RestController
@RequestMapping(value = "/send")
public class SendController {
	@Autowired
	private SendService sendService;

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * 流程相关
	 **/

	/**
	 * 接收寄件表单中的信息
	 * @param request   寄件信息
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 *     返回运费和成功与否消息
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getSendInfo")
	public Map<String, Object> getSendInfo(@RequestBody SendRequest request) {
		return sendService.sendPack(request);
	}

	/**
	 * User 支付寄件
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/pay")
	public Map<String, String> pay(@RequestParam(value = "id") String id,
	                               @RequestParam(value = "token") String token) {
		return sendService.sendPay(id, token);
	}

	/**
	 * Admin 确认寄件
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/confirm")
	public Map<String, String> confirm(@RequestParam(value = "id") String id,
	                                   @RequestParam(value = "token") String token) {
		return sendService.sendConfirm(id, token);
	}

	/**
	 * Admin 发出寄件
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/out")
	public Map<String, String> out(@RequestParam(value = "id") String id,
	                               @RequestParam(value = "token") String token) {
		return sendService.sendOut(id, token);
	}

	/**
	 * User 取消寄件
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/cancel")
	public Map<String, String> cancel(@RequestParam(value = "id") String id,
	                                  @RequestParam(value = "token") String token) {
		return sendService.sendCancel(id, token);
	}

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
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getSendByUser/{currentPage}")
	public Map<String, Object> getSendByUser(@PathVariable int currentPage,
	                                         @RequestParam(value = "pageSize") int pageSize,
	                                         @RequestParam(value = "token") String token,
	                                         @RequestParam(value = "org") String org) {
		return sendService.getSendByUser(currentPage, pageSize, token, org);
	}

	/**
	 * 获取 User 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getTotalByUser")
	public Map<String, Object> getTotalByUser(@RequestParam(value = "token") String token) {
		return sendService.getTotalByUser(token);
	}

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
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getSendByAdmin/{currentPage}")
	public Map<String, Object> getSendByAdmin(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return sendService.getSendByAdmin(currentPage, pageSize, token);
	}

	/**
	 * 获取 Admin 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getTotalByAdmin")
	public Map<String, Object> getTotalByAdmin(@RequestParam(value = "token") String token) {
		return sendService.getTotalByUser(token);
	}

}
