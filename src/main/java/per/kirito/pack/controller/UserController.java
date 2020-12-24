package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.pojo.User;
import per.kirito.pack.service.inter.UserService;
import per.kirito.pack.util.TypeConversion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 21:04
 * @description:
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final int LOGIN_CODE = Status.LOGIN_SUCCESS.getCode();
	private static final int EXIT_CODE = Status.EXIT_SUCCESS.getCode();
	private static final int INFO_CODE = Status.INFO_SUCCESS.getCode();
	private static final int REGISTER_CODE = Status.REGISTER_SUCCESS.getCode();
	private static final int EXIST_CODE = Status.IS_EXIST.getCode();
	private static final int PWD_CODE = Status.PWD_SUCCESS.getCode();

	private static final String LOGIN_SUCCESS = Status.LOGIN_SUCCESS.getMsg();
	private static final String LOGIN_FAIL = Status.LOGIN_FAIL.getMsg();
	private static final String EXIT_SUCCESS = Status.EXIT_SUCCESS.getMsg();
	private static final String EXIT_FAIL = Status.EXIT_FAIL.getMsg();
	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getMsg();
	private static final String REGISTER_SUCCESS = Status.REGISTER_SUCCESS.getMsg();
	private static final String REGISTER_FAIL = Status.REGISTER_FAIL.getMsg();
	private static final String IS_EXIST = Status.IS_EXIST.getMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getMsg();
	private static final String PWD_SUCCESS = Status.PWD_SUCCESS.getMsg();
	private static final String PWD_FAIL = Status.PWD_FAIL.getMsg();


	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "card") String card,
	                        @RequestParam(value = "password") String password) {
		String encrypt = TypeConversion.stringToMD5(password);
		User user = new User();
		user.setCard(card);
		user.setPassword(encrypt);
		int flag = userService.findUserByCardAndPwd(user);
		if (flag == LOGIN_CODE) {
			stringRedisTemplate.opsForValue().set("card", card);
			return LOGIN_SUCCESS;
		} else {
			return LOGIN_FAIL;
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public String userExit() {
		stringRedisTemplate.delete("card");
		return stringRedisTemplate.hasKey("card") ? EXIT_FAIL : EXIT_SUCCESS;
	}

	@CrossOrigin
	@RequestMapping(value = "/getInfo", method = RequestMethod.POST)
	public Map<String, Object> getUserInfo() {
		String card = stringRedisTemplate.opsForValue().get("card");
		Map<String, Object> map = new HashMap<>();
		if (card == null || card == "") {
			map.put("result", INFO_FAIL);
		} else {
			User user = userService.getUser(card);
			map.put("user", user);
		}
		return map;
	}

	@CrossOrigin
	@RequestMapping(value = "/addUser")
	public String addUser(@RequestBody User user) {
		String pwd = user.getPassword();
		String encrypt = TypeConversion.stringToMD5(pwd);
		user.setPassword(encrypt);
		int flag = userService.addUser(user);
		if (flag == REGISTER_CODE) {
			String card = user.getCard();
			stringRedisTemplate.opsForValue().set("card", card);
			return REGISTER_SUCCESS;
		} else {
			return REGISTER_FAIL;
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/forgetPwd")
	public Map<String, String> forgetPwd(@RequestParam(value = "card") String card,
	                                     @RequestParam(value = "phone") String phone,
	                                     @RequestParam(value = "password") String password) {
		User user = new User();
		user.setCard(card);
		user.setPhone(phone);
		int ifExit = userService.findUserByCardAndPhone(user);
		Map<String, String> map = new HashMap<>();
		if (ifExit == EXIST_CODE) {
			String encrypt = TypeConversion.stringToMD5(password);
			user = userService.getUser(card);
			user.setPassword(encrypt);
			int flag = userService.updateUser(user);
			if (flag == PWD_CODE) {
				map.put("flag", PWD_SUCCESS);
				stringRedisTemplate.opsForValue().set("card", card);
			} else {
				map.put("flag", PWD_FAIL);
			}
		} else {
			map.put("flag", NOT_EXIST);
		}
		return map;
	}
}
