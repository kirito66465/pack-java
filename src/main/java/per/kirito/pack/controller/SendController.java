package per.kirito.pack.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.utilPojo.SendRequest;
import per.kirito.pack.service.inter.SendService;
import per.kirito.pack.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * author: kirito
 * date: 2021/1/4
 * time: 15:51
 * 寄件 Send 的 controller 层
 */
@Slf4j
@Api(tags = {"寄件管理"}, description = "寄件管理", produces = "application/json", consumes = "application/json")
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
	 *
	 * @param sendRequest 寄件信息
	 * @param request     http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * 返回运费和成功与否消息
	 **/
	@ApiOperation(value = "接收寄件表单", notes = "接收学生寄件申请，返回写入数据库成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getSendInfo")
	public Map<String, Object> getSendInfo(
			@ApiParam(required = true, name = "request", value = "寄件表单实体") @RequestBody SendRequest sendRequest,
			HttpServletRequest request) {
		log.info("请求 URL[/send/getSendInfo]；参数[request={}]", sendRequest);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.sendPack(sendRequest);
	}

	/**
	 * User 支付寄件
	 *
	 * @param id      快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "学生支付", notes = "学生支付寄件请求，返回更新状态成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PutMapping(value = "/pay")
	public String pay(
			@ApiParam(required = true, name = "id", value = "快递单号") @RequestParam(value = "id") String id,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/send/pay]；参数[id={}, token={}]", id, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.sendPay(id, token);
	}

	/**
	 * Admin 确认寄件
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "驿站确认", notes = "驿站确认所属寄件请求，返回更新状态成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PutMapping(value = "/confirm")
	public String confirm(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/send/confirm]；参数[ids={}, token={}]", ids, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.sendConfirm(ids, token);
	}

	/**
	 * Admin 发出寄件
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "驿站发出", notes = "驿站发出所属寄件请求，返回更新状态成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PutMapping(value = "/out")
	public String out(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/send/out]；参数[ids={}, token={}]", ids, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.sendOut(ids, token);
	}

	/**
	 * User 取消寄件
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "学生取消", notes = "学生取消寄件请求，返回取消成功与否", httpMethod = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@DeleteMapping(value = "/cancel")
	public String cancel(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/send/cancel]；参数[ids={}, token={}]", ids, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.sendCancel(ids, token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 *
	 **/

	/**
	 * 分页方式获取 User 寄件集合
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:寄件状态, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	@ApiOperation(value = "学生寄件集", notes = "获取寄件列表请求，如果成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getSendByUser")
	public Map<String, Object> getSendByUser(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:寄件状态, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/send/getSendByUser]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.getSendByUser(json);
	}

	/**
	 * 获取 User 寄件数量
	 *
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	@ApiOperation(value = "学生寄件数量", notes = "获取学生寄件数量请求，返回各个状态的寄件数量", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getTotalByUser")
	public Map<String, Object> getTotalByUser(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/send/getTotalByUser]；参数[token={}]", token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.getTotalByUser(token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 **/

	/**
	 * 分页方式获取 Admin 寄件集合
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, status:寄件状态, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	@ApiOperation(value = "驿站寄件集", notes = "获取驿站所属寄件列表请求，如果成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getSendByAdmin")
	public Map<String, Object> getSendByAdmin(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, status:寄件状态, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/send/getSendByAdmin]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.getSendByAdmin(json);
	}

	/**
	 * 获取 Admin 寄件数量
	 *
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	@ApiOperation(value = "驿站寄件数量", notes = "获取驿站所属寄件数量请求，返回各个状态的寄件数量", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getTotalByAdmin")
	public Map<String, Object> getTotalByAdmin(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/send/getTotalByAdmin]；参数[token={}]", token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return sendService.getTotalByAdmin(token);
	}

}
