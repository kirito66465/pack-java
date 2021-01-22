package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AccountService;

import java.util.Map;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/11 20:11
 * @description: Admin 的 Controller 层
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Qualifier("adminServiceImpl")
	@Autowired
	private AccountService<Admin> accountService;

	/**
	 * @Description: Admin 登录
	 * @Param: [card, password]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/login")
	public Map<String, String> adminLogin(@RequestParam(value = "card") String card,
	                        @RequestParam(value = "password") String password) {
		return accountService.login(card, password);
	}

	/**
	 * @Description: Admin 退出登录
	 * @Param: []
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/exit")
	public String adminExit(@RequestParam(value = "token") String token) {
		return accountService.exit(token);
	}

	/**
	 * @Description: 获取 Admin 信息
	 * @Param: []
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getInfo")
	public Map<String, Object> getAdminInfo(@RequestParam(value = "token") String token) {
		return accountService.getInfo(token);
	}

	/**
	 * @Description: 修改密码
	 * @Param: [card, oldPwd, newPwd, checkCode, token]
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
	 * @Description: 更新 Admin 信息
	 * @Param: [name, phone, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/updateInfo")
	public Map<String, String> updateInfo(@RequestParam(value = "name") String name,
	                                      @RequestParam(value = "phone") String phone,
	                                      @RequestParam(value = "mail") String mail,
	                                      @RequestParam(value = "token") String token) {
		return accountService.updateInfo(name, phone, mail, token);
	}

}
