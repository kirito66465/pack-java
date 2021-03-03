package per.kirito.pack.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.User;
import per.kirito.pack.service.inter.AccountService;

import java.util.Map;

/**
 * author: 严晨
 * date: 2020/12/4
 * time: 21:04
 * User 的 Controller 层
 */
@Api(tags = {"学生管理"}, description = "学生管理", produces = "application/json", consumes = "application/json")
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private static Log log = LogFactory.get();

	@Qualifier("userServiceImpl")
	@Autowired
	private AccountService<User> accountService;

	/**
	 * User 登录
	 * @param card      学号
	 * @param password  密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "登录", notes = "登录请求，返回登录成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/login")
	public Map<String, String> login(
			@ApiParam(required = true, name = "card", value = "学号") @RequestParam(value = "card") String card,
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password) {
		log.info("请求 URL[/user/login]；参数[card=" + card + ", password=" + password + "]");
		return accountService.login(card, password);
	}

	/**
	 * User 退出登录
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
	public String userLogout(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/user/logout]；参数[token=" + token + "]");
		return accountService.logout(token);
	}

	/**
	 * 获取 User 信息
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "获取学生信息", notes = "获取信息，如果获取成功返回账号信息，如果失败返回失败原因", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getInfo")
	public Map<String, Object> getUserInfo(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/user/getInfo]；参数[token=" + token + "]");
		return accountService.getInfo(token);
	}

	/**
	 * User 注册
	 * @param user  用户信息
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "注册", notes = "注册请求，返回注册成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/register")
	public Map<String, String> register(
			@ApiParam(required = true, name = "user", value = "学生实体") @RequestBody User user) {
		log.info("请求 URL[/user/register]；参数[user=" + user.toString() + "]");
		return accountService.register(user);
	}

	/**
	 * User 忘记密码
	 * @param card      学号
	 * @param phone     手机号
	 * @param password  新密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "忘记密码", notes = "忘记密码请求，如果学生存在返回操作成功与否，如果不存在返回学生不存在信息", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PutMapping(value = "/forgetPwd")
	public Map<String, String> forgetPwd(
			@ApiParam(required = true, name = "card", value = "学号") @RequestParam(value = "card") String card,
			@ApiParam(required = true, name = "phone", value = "手机号") @RequestParam(value = "phone") String phone,
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password) {
		log.info("请求 URL[/user/forgetPwd]；参数[card=" + card + ", phone" + phone + ", password=" + password + "]");
		return accountService.forgetPwd(card, phone, password);
	}

	/**
	 * 修改密码
	 * @param card      学号
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
			@ApiParam(required = true, name = "card", value = "学号") @RequestParam(value = "card") String card,
			@ApiParam(required = true, name = "oldPwd", value = "原密码") @RequestParam(value = "oldPwd") String oldPwd,
			@ApiParam(required = true, name = "newPwd", value = "新密码") @RequestParam(value = "newPwd") String newPwd,
			@ApiParam(required = true, name = "checkCode", value = "验证码") @RequestParam(value = "checkCode") String checkCode,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/user/resetPwd]；参数[card=" + card + ", oldPwd" + oldPwd + ", newPwd=" + newPwd
				+ ", checkCode=" + checkCode + ", token=" + token + "]");
		return accountService.resetPwd(card, oldPwd, newPwd, checkCode, token);
	}

	/**
	 * 更新用户信息
	 * @param name  姓名
	 * @param addr  地址
	 * @param mail  邮箱
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "更新学生信息", notes = "更新信息请求，返回更新成功与否", httpMethod = "PUT")
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
			@ApiParam(required = true, name = "name", value = "姓名") @RequestParam(value = "name") String name,
			@ApiParam(required = true, name = "addr", value = "地址") @RequestParam(value = "addr") String addr,
			@ApiParam(required = true, name = "mail", value = "邮箱") @RequestParam(value = "mail") String mail,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/user/updateInfo]；参数[name=" + name + ", addr" + addr + ", mail=" + mail
				+ ", token=" + token + "]");
		return accountService.updateInfo(name, addr, mail, token);
	}

}
