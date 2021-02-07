package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.kirito.pack.mapper.AdminMapper;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AccountService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/28
 * @Time: 16:02
 * @description: Admin 的 Service 层，是 AccountService 的泛型接口实现
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
	private static final String LOGIN_TO_DO = Status.LOGIN_TO_DO.getEnMsg();
	private static final String DO_SUCCESS = Status.DO_SUCCESS.getEnMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getEnMsg();
	private static final String CODE_ERR = Status.CODE_ERR.getEnMsg();
	private static final String CODE_INVALID = Status.CODE_INVALID.getEnMsg();
	private static final String PWD_ERR = Status.PWD_ERR.getEnMsg();

	/**
	 * Admin 登录
	 * @param card      编号
	 * @param password  密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Override
	public Map<String, String> login(String card, String password) {
		Map<String, String> map = new HashMap<>();
		// 根据card和password查询出该Admin是否存在
		int flag = adminMapper.login(card, password);
		if (flag == LOGIN_CODE) {
			// 生成唯一令牌token
			String token = UUID.randomUUID().toString();
			// 如果Redis中已存储，则先删除此键
			if (stringRedisTemplate.hasKey(token)) {
				stringRedisTemplate.delete(token);
			}
			stringRedisTemplate.opsForValue().set(token, card, 10, TimeUnit.MINUTES);
			map.put("token", token);
			map.put("result", LOGIN_SUCCESS);
		} else {
			map.put("result", LOGIN_FAIL);
		}
		return map;
	}

	/**
	 * 退出登录
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	@Override
	public String exit(String token) {
		// 退出登录时，删除Redis中存储的相关键值
		stringRedisTemplate.delete(token);
		return stringRedisTemplate.hasKey(token) ? EXIT_FAIL : EXIT_SUCCESS;
	}

	/**
	 * 获取 Admin 信息
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	public Map<String, Object> getInfo(String token) {
		Map<String, Object> map = new HashMap<>();
		String result = "";
		boolean isLogin = stringRedisTemplate.hasKey(token);
		if (isLogin) {
			String card = stringRedisTemplate.opsForValue().get(token);
			Admin admin = adminMapper.getAdminById(card);
			map.put("admin", admin);
			result = INFO_SUCCESS;
		} else {
			result = INFO_FAIL;
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 注册（不实现该功能）
	 * @param entity    账户实体信息
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Override
	public Map<String, String> register(E entity) {
		// 不实现该功能
		return null;
	}

	/**
	 * 忘记密码（不实现该功能）
	 * @param card      编号
	 * @param phone     手机号
	 * @param password  新密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Override
	public Map<String, String> forgetPwd(String card, String phone, String password) {
		// 不实现该功能
		return null;
	}

	/**
	 * 修改密码
	 * @param card      编号
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
	 * @param checkCode 验证码
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> resetPwd(String card, String oldPwd, String newPwd, String checkCode, String token) {
		Map<String, String> map = new HashMap<>();
		try {
			if (stringRedisTemplate.hasKey(token)) {
				String tokenCode = token + "-code";
				if (stringRedisTemplate.hasKey(tokenCode)) {
					// Redis 中存有验证码，且未过期
					String code = stringRedisTemplate.opsForValue().get(token + "-code");
					if (code.equals(checkCode)) {
						int flag = adminMapper.resetPwd(card, oldPwd, newPwd);
						if (flag == 1) {
							map.put("result", PWD_SUCCESS);
						} else {
							// 原密码错误，导致成功执行条数不为1
							map.put("result", PWD_ERR);
						}
					} else {
						// 验证码不正确
						map.put("result", CODE_ERR);
					}
				} else {
					// 验证码已过期
					map.put("result", CODE_INVALID);
				}
			} else {
				// 登录状态失效
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", DO_FAIL);
			return map;
		}
	}

	/**
	 * 更新 Admin 信息
	 * @param name  姓名
	 * @param phone 手机号
	 * @param mail  邮箱
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> updateInfo(String name, String phone, String mail, String token) {
		Map<String, String> map = new HashMap<>();
		try {
			if (stringRedisTemplate.hasKey(token)) {
				String card = stringRedisTemplate.opsForValue().get(token);
				adminMapper.updateInfo(card, name, phone);
				map.put("result", DO_SUCCESS);
			} else {
				// 登录状态失效
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", DO_FAIL);
			return map;
		}
	}

}
