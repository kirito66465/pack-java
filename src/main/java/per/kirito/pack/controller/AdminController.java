package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AdminService;
import per.kirito.pack.util.TypeConversion;

import java.util.HashMap;
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
	@Autowired
	private AdminService adminService;

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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String adminLogin(@RequestParam(value = "card") String card,
	                        @RequestParam(value = "password") String password) {
		String encrypt = TypeConversion.stringToMD5(password);
		Admin admin = new Admin();
		admin.setCard(card);
		admin.setPassword(encrypt);
		int flag = adminService.findAdminByCardAndPwd(admin);
		if (flag == LOGIN_CODE) {
			stringRedisTemplate.opsForValue().set("card", card);
			return LOGIN_SUCCESS;
		} else {
			return LOGIN_FAIL;
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public String adminExit() {
		stringRedisTemplate.delete("card");
		return stringRedisTemplate.hasKey("card") ? EXIT_FAIL : EXIT_SUCCESS;
	}

	@CrossOrigin
	@RequestMapping(value = "/getInfo", method = RequestMethod.POST)
	public Map<String, Object> getAdminInfo() {
		String card = stringRedisTemplate.opsForValue().get("card");
		Map<String, Object> map = new HashMap<>();
		if (card == null || card == "") {
			map.put("result", INFO_FAIL);
		} else {
			Admin admin = adminService.getAdmin(card);
			map.put("result", admin);
		}
		return map;
	}
}
