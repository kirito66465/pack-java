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
 * @description: Admin的Controller层
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Qualifier("adminServiceImpl")
	@Autowired
	private AccountService<Admin> accountService;

	/**
	 * @Description: Admin登录请求
	 * @Param: [card, password]
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/login")
	public Map<String, String> adminLogin(@RequestParam(value = "card") String card,
	                        @RequestParam(value = "password") String password) {
		return accountService.login(card, password);
	}

	/**
	 * @Description: Admin退出登录请求
	 * @Param: []
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/exit")
	public String adminExit(@RequestParam(value = "token") String token) {
		return accountService.exit(token);
	}

	/**
	 * @Description: 获取Admin信息请求
	 * @Param: []
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getInfo")
	public Map<String, Object> getAdminInfo(@RequestParam(value = "token") String token) {
		return accountService.getInfo(token);
	}

}
