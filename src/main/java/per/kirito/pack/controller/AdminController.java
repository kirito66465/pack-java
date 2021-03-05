package per.kirito.pack.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AccountService;
import per.kirito.pack.util.PackIdUtil;

import java.util.Map;

/**
 * author: 严晨
 * date: 2020/12/11
 * time: 20:11
 * Admin 的 Controller 层
 */
@Slf4j
@Api(tags = {"管理员管理"}, description = "管理员管理", produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Qualifier("adminServiceImpl")
	@Autowired
	private AccountService<Admin> accountService;

	/**
	 * Admin 登录
	 * @param card      编号
	 * @param password  密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "登录", notes = "登录请求，返回登录成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/login")
	public Map<String, String> adminLogin(
			@ApiParam(required = true, name = "card", value = "驿站编号") @RequestParam(value = "card") String card,
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password) {
		log.info("请求 URL[/admin/login]；参数[card=" + card + ", password=" + password + "]");
		return accountService.login(card, password);
	}

	/**
	 * Admin 退出登录
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "退出登录", notes = "退出登录请求，返回退出成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/logout")
	public String adminLogout(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/admin/logout]；参数[token=" + token + "]");
		return accountService.logout(token);
	}

	/**
	 * 获取 Admin 信息
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "获取驿站信息", notes = "获取信息，如果获取成功返回账号信息，如果失败返回失败原因", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getInfo")
	public Map<String, Object> getAdminInfo(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/admin/getInfo]；参数[token=" + token + "]");
		return accountService.getInfo(token);
	}

	/**
	 * 修改密码
	 * @param card      编号
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
	 * @param checkCode 验证码
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "修改密码", notes = "修改密码请求，返回修改密码成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PutMapping(value = "/resetPwd")
	public Map<String, String> resetPwd(
			@ApiParam(required = true, name = "card", value = "驿站编号") @RequestParam(value = "card") String card,
			@ApiParam(required = true, name = "oldPwd", value = "原密码") @RequestParam(value = "oldPwd") String oldPwd,
			@ApiParam(required = true, name = "newPwd", value = "新密码") @RequestParam(value = "newPwd") String newPwd,
			@ApiParam(required = true, name = "checkCode", value = "验证码") @RequestParam(value = "checkCode") String checkCode,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/admin/resetPwd]；参数[card=" + card + ", oldPwd" + oldPwd + ", newPwd=" + newPwd
				+ ", checkCode=" + checkCode + ", token=" + token + "]");
		return accountService.resetPwd(card, oldPwd, newPwd, checkCode, token);
	}

	/**
	 * 更新 Admin 信息
	 * @param name      姓名
	 * @param phone     手机号
	 * @param mail      邮箱
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "更新驿站信息", notes = "更新信息请求，返回更新成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PutMapping(value = "/updateInfo")
	public Map<String, String> updateInfo(
			@ApiParam(required = true, name = "name", value = "驿站编号") @RequestParam(value = "name") String name,
			@ApiParam(required = true, name = "phone", value = "手机号") @RequestParam(value = "phone") String phone,
			@ApiParam(required = true, name = "mail", value = "邮箱") @RequestParam(value = "mail") String mail,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/admin/updateInfo]；参数[name=" + name + ", phone" + phone + ", mail=" + mail
				+ ", token=" + token + "]");
		return accountService.updateInfo(name, phone, mail, token);
	}

	/**
	 * 生成快递单号
	 * @param type  快递所属公司
	 * @return java.lang.String
	 */
	@ApiOperation(value = "生成快递单号", notes = "生成指定快递的单号请求，返回快递单号", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getPackId")
	public String getPackId(
			@ApiParam(required = true, name = "type", value = "快递公司") @RequestParam(value = "type") String type) {
		log.info("请求 URL[/admin/getPackId]；参数[type=" + type + "]");
		return PackIdUtil.generate(type);
	}

}
