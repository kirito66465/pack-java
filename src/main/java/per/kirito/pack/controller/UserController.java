package per.kirito.pack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"学生管理"}, description = "学生管理")
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Qualifier("userServiceImpl")
	@Autowired
	private AccountService<User> accountService;

	/**
	 * User 登录
	 * @param card      学号
	 * @param password  密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "登录")
	@CrossOrigin
	@RequestMapping(value = "/login")
	public Map<String, String> login(@RequestParam(value = "card") String card,
	                                 @RequestParam(value = "password") String password) {
		return accountService.login(card, password);
	}

	/**
	 * User 退出登录
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "退出登录")
	@CrossOrigin
	@RequestMapping(value = "/logout")
	public String userLogout(@RequestParam(value = "token") String token) {
		return accountService.logout(token);
	}

	/**
	 * 获取 User 信息
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "获取账号信息")
	@CrossOrigin
	@RequestMapping(value = "/getInfo")
	public Map<String, Object> getUserInfo(@RequestParam(value = "token") String token) {
		return accountService.getInfo(token);
	}

	/**
	 * User 注册
	 * @param user  用户信息
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "注册")
	@CrossOrigin
	@RequestMapping(value = "/register")
	public Map<String, String> register(@RequestBody User user) {
		return accountService.register(user);
	}

	/**
	 * User 忘记密码
	 * @param card      学号
	 * @param phone     手机号
	 * @param password  新密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "忘记密码")
	@CrossOrigin
	@RequestMapping(value = "/forgetPwd")
	public Map<String, String> forgetPwd(@RequestParam(value = "card") String card,
	                                     @RequestParam(value = "phone") String phone,
	                                     @RequestParam(value = "password") String password) {
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
	@ApiOperation(value = "修改密码")
	@CrossOrigin
	@RequestMapping(value = "/resetPwd")
	public Map<String, String> resetPwd(@RequestParam(value = "card") String card,
	                                    @RequestParam(value = "oldPwd") String oldPwd,
	                                    @RequestParam(value = "newPwd") String newPwd,
	                                    @RequestParam(value = "checkCode") String checkCode,
	                                    @RequestParam(value = "token") String token) {
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
	@ApiOperation(value = "更新账号信息")
	@CrossOrigin
	@RequestMapping(value = "/updateInfo")
	public Map<String, String> updateInfo(@RequestParam(value = "name") String name,
	                                      @RequestParam(value = "addr") String addr,
	                                      @RequestParam(value = "mail") String mail,
	                                      @RequestParam(value = "token") String token) {
		return accountService.updateInfo(name, addr, mail, token);
	}

}
