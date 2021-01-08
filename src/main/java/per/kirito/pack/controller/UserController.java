package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.User;
import per.kirito.pack.service.inter.AccountService;

import java.util.Map;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 21:04
 * @description: User的Controller层
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Qualifier("userServiceImpl")
	@Autowired
	private AccountService<User> accountService;

	/**
	 * @Description: User登录请求
	 * @Param: [card, password]
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/login")
	public Map<String, String> login(@RequestParam(value = "card") String card,
	                                 @RequestParam(value = "password") String password) {
		return accountService.login(card, password);
	}

	/**
	 * @Description: User退出登录请求
	 * @Param: []
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/exit")
	public String userExit(@RequestParam(value = "token") String token) {
		return accountService.exit(token);
	}

	/**
	 * @Description: 获取User信息请求
	 * @Param: []
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getInfo")
	public Map<String, Object> getUserInfo(@RequestParam(value = "token") String token) {
		return accountService.getInfo(token);
	}

	/**
	 * @Description: User注册请求
	 * @Param: [user]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/register")
	public Map<String, String> register(@RequestBody User user) {
		return accountService.register(user);
	}

	/**
	 * @Description: User忘记密码请求
	 * @Param: [card, phone, password]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/forgetPwd")
	public Map<String, String> forgetPwd(@RequestParam(value = "card") String card,
	                                     @RequestParam(value = "phone") String phone,
	                                     @RequestParam(value = "password") String password) {
		return accountService.forgetPwd(card, phone, password);
	}

	/**
	 * @Description: 重置密码
	 * @Param: [card, password, checkCode, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
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
	 * @Description: 更新用户信息
	 * @Param: [name, addr, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/updateInfo")
	public Map<String, String> updateInfo(@RequestParam(value = "name") String name,
	                                      @RequestParam(value = "addr") String addr,
	                                      @RequestParam(value = "token") String token) {
		return accountService.updateInfo(name, addr, token);
	}

}
