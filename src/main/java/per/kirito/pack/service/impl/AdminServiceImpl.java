package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.AdminMapper;
import per.kirito.pack.other.myEnum.Status;
import per.kirito.pack.other.util.TypeConversion;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/28
 * @Time: 16:02
 * @description:
 */
@Service
public class AdminServiceImpl<E> implements AccountService<E> {
	@Autowired
	private AdminMapper adminMapper;

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

	@Override
	public String login(String card, String password) {
		String result = "";
		String encrypt = TypeConversion.stringToMD5(password);
		Admin admin = new Admin();
		admin.setCard(card);
		admin.setPassword(encrypt);
		int flag = adminMapper.findAdminByCardAndPwd(admin);
		if (flag == LOGIN_CODE) {
			// 如果Redis中已存储，则先删除此键
			if (stringRedisTemplate.hasKey("admin-card")) {
				stringRedisTemplate.delete("admin-card");
			}
			stringRedisTemplate.opsForValue().set("admin-card", card, 30, TimeUnit.MINUTES);
			result = LOGIN_SUCCESS;
		} else {
			result = LOGIN_FAIL;
		}
		return result;
	}

	@Override
	public String exit() {
		stringRedisTemplate.delete("admin-card");
		return stringRedisTemplate.hasKey("admin-card") ? EXIT_FAIL : EXIT_SUCCESS;
	}

	@Override
	public Map<String, Object> getInfo() {
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		Map<String, Object> map = new HashMap<>();
		if (card == null || card == "") {
			map.put("result", INFO_FAIL);
		} else {
			Admin admin = adminMapper.getAdminById(card);
			map.put("result", admin);
		}
		return map;
	}

	@Override
	public String register(E entity) {
		return null;
	}

	@Override
	public Map<String, String> forgetPwd(String card, String phone, String password) {
		return null;
	}
}
