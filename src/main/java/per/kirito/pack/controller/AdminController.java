package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.other.myEnum.Status;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AccountService;

import java.util.Map;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/11 20:11
 * @description:
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Qualifier("adminServiceImpl")
	@Autowired
	private AccountService<Admin> accountService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final int LOGIN_CODE = Status.LOGIN_SUCCESS.getCode();
	private static final int EXIT_CODE = Status.EXIT_SUCCESS.getCode();
	private static final int INFO_CODE = Status.INFO_SUCCESS.getCode();
	private static final int EXIST_CODE = Status.IS_EXIST.getCode();

	private static final String LOGIN_SUCCESS = Status.LOGIN_SUCCESS.getMsg();
	private static final String LOGIN_FAIL = Status.LOGIN_FAIL.getMsg();
	private static final String EXIT_SUCCESS = Status.EXIT_SUCCESS.getMsg();
	private static final String EXIT_FAIL = Status.EXIT_FAIL.getMsg();
	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getMsg();
	private static final String IS_EXIST = Status.IS_EXIST.getMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getMsg();

	@CrossOrigin
	@RequestMapping(value = "/login")
	public String adminLogin(@RequestParam(value = "card") String card,
	                        @RequestParam(value = "password") String password) {
		return accountService.login(card, password);
	}

	@CrossOrigin
	@RequestMapping(value = "/exit")
	public String adminExit() {
		return accountService.exit();
	}

	@CrossOrigin
	@RequestMapping(value = "/getInfo")
	public Map<String, Object> getAdminInfo() {
		return accountService.getInfo();
	}
}
