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

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/28
 * @Time: 16:02
 * @description: Admin的Service层，是AccountService的泛型接口实现
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

	private static final String LOGIN_SUCCESS = Status.LOGIN_SUCCESS.getEnMsg();
	private static final String LOGIN_FAIL = Status.LOGIN_FAIL.getEnMsg();
	private static final String EXIT_SUCCESS = Status.EXIT_SUCCESS.getEnMsg();
	private static final String EXIT_FAIL = Status.EXIT_FAIL.getEnMsg();
	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getEnMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getEnMsg();
	private static final String REGISTER_SUCCESS = Status.REGISTER_SUCCESS.getEnMsg();
	private static final String REGISTER_FAIL = Status.REGISTER_FAIL.getEnMsg();
	private static final String IS_EXIST = Status.IS_EXIST.getEnMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getEnMsg();
	private static final String PWD_SUCCESS = Status.PWD_SUCCESS.getEnMsg();
	private static final String PWD_FAIL = Status.PWD_FAIL.getEnMsg();

	/**
	 * @Description: 登录
	 * @Param: [card, password]
	 * @Return: java.lang.String
	 **/
	@Override
	public String login(String card, String password) {
		String result = "";
		// 对传入的password进行加密
		String encrypt = TypeConversion.stringToMD5(password);
		Admin admin = new Admin();
		admin.setCard(card);
		admin.setPassword(encrypt);
		// 根据card和password查询出该Admin是否存在
		int flag = adminMapper.findAdminByCardAndPwd(admin);
		if (flag == LOGIN_CODE) {
			// 如果Redis中已存储，则先删除此键
			if (stringRedisTemplate.hasKey("admin-card")) {
				stringRedisTemplate.delete("admin-card");
			}
			stringRedisTemplate.opsForValue().set("admin-card", card);
			result = LOGIN_SUCCESS;
		} else {
			result = LOGIN_FAIL;
		}
		return result;
	}

	/**
	 * @Description: 退出登录
	 * @Param: []
	 * @Return: java.lang.String
	 **/
	@Override
	public String exit() {
		// 退出登录时，删除Redis中存储的相关键值
		stringRedisTemplate.delete("admin-card");
		return stringRedisTemplate.hasKey("admin-card") ? EXIT_FAIL : EXIT_SUCCESS;
	}

	/**
	 * @Description: 获取信息
	 * @Param: []
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	public Map<String, Object> getInfo() {
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		Map<String, Object> map = new HashMap<>();
		if (card == null || card == "") {
			// 从Redis中获取card失败，即获取信息失败
			map.put("result", INFO_FAIL);
		} else {
			// card不为空，即根据card查询出该Admin的信息
			Admin admin = adminMapper.getAdminById(card);
			map.put("result", admin);
		}
		return map;
	}

	/**
	 * @Description: 注册
	 * @Param:
	 * @Return:
	 **/
	@Override
	public String register(E entity) {
		// 不实现该功能
		return null;
	}

	/**
	 * @Description: 重置密码
	 * @Param: [card, phone, password]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Override
	public Map<String, String> forgetPwd(String card, String phone, String password) {
		// 不实现该功能
		return null;
	}

}
