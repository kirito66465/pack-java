package per.kirito.pack.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.myenum.Status;
import per.kirito.pack.pojo.User;
import per.kirito.pack.service.inter.AccountService;
import per.kirito.pack.util.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author kirito
 * @date 2020/12/28
 * @time 15:24
 * User 的 Service 层，是 AccountService 的泛型接口实现
 */
@Slf4j
@Service
public class UserServiceImpl<E extends User> implements AccountService<E> {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final int LOGIN_CODE = Status.LOGIN_SUCCESS.getCode();

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
	 * User 登录
	 *
	 * @param card     编号
	 * @param password 密码
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	@Override
	public Map<String, String> login(String card, String password) {
		Map<String, String> map = new HashMap<>();
		// 根据 card 和 password 查询出该 User 是否存在
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("card", card).eq("password", password);
		int flag = userMapper.selectCount(queryWrapper);
		if (flag == LOGIN_CODE) {
			// 生成唯一令牌 token
			String token = UUID.randomUUID().toString();
			// 如果 Redis 中已存储，则先删除此键
			if (stringRedisTemplate.hasKey(token)) {
				stringRedisTemplate.delete(token);
			}
			stringRedisTemplate.opsForValue().set(token, card, Constant.LOGIN_VALID_MINUTE, TimeUnit.MINUTES);
			map.put("token", token);
			map.put("result", LOGIN_SUCCESS);
		} else {
			log.info("card: {} 登录失败，因为该学生不存在！", card);
			map.put("result", LOGIN_FAIL);
		}
		return map;
	}

	/**
	 * 退出登录
	 *
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Override
	public String logout(String token) {
		// 退出登录时，删除 Redis 中存储的相关键值
		stringRedisTemplate.delete(token);
		return stringRedisTemplate.hasKey(token) ? EXIT_FAIL : EXIT_SUCCESS;
	}

	/**
	 * 获取 User 信息
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getInfo(String token) {
		Map<String, Object> map = new HashMap<>();
		String result = "";
		boolean isLogin = stringRedisTemplate.hasKey(token);
		if (isLogin) {
			String card = stringRedisTemplate.opsForValue().get(token);
			User user = userMapper.selectById(card);
			map.put("user", user);
			result = INFO_SUCCESS;
		} else {
			log.info("token: {} 获取该学生信息失败，因为登录状态失效！", token);
			result = INFO_FAIL;
		}
		map.put("result", result);
		return map;
	}

	/**
	 * 注册
	 *
	 * @param entity 账户实体信息
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> register(E entity) {
		Map map = new HashMap();
		try {
			String result = "";
			String card = entity.getCard();
			String phone = entity.getPhone();
			QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
			userQueryWrapper.eq("card", card).or(i -> i.eq("phone", phone));
			int count = userMapper.selectCount(userQueryWrapper);
			User user;
			if (count == 0) {
				userMapper.insert(entity);
				// 生成唯一令牌 token
				String token = UUID.randomUUID().toString();
				// 如果 Redis 中已存储，则先删除此键
				if (stringRedisTemplate.hasKey(token)) {
					stringRedisTemplate.delete(token);
				}
				stringRedisTemplate.opsForValue().set(token, card, Constant.LOGIN_VALID_MINUTE, TimeUnit.MINUTES);
				map.put("token", token);
				map.put("name", entity.getName());
				result = REGISTER_SUCCESS;
			} else {
				log.info("card: {} 注册失败，因为该学生已存在，学号或手机号已被注册！", entity.getCard());
				result = IS_EXIST;
			}
			map.put("result", result);
			return map;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("card: {} 注册失败，因为发生了异常！", entity.getCard());
			map.put("result", REGISTER_FAIL);
			return map;
		}
	}

	/**
	 * 账户忘记密码
	 *
	 * @param card     编号
	 * @param phone    手机号
	 * @param password 新密码
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> forgetPwd(String card, String phone, String password) {
		Map<String, String> map = new HashMap<>();
		try {
			String result = "";
			QueryWrapper<User> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("card", card).eq("phone", phone);
			int isDo = userMapper.selectCount(queryWrapper);
			if (isDo == 1) {
				User user = userMapper.selectById(card);
				user.setPassword(password);
				userMapper.updateById(user);
				// 生成唯一令牌 token
				String token = UUID.randomUUID().toString();
				// 如果 Redis 中已存储，则先删除此键
				if (stringRedisTemplate.hasKey(token)) {
					stringRedisTemplate.delete(token);
				}
				stringRedisTemplate.opsForValue().set(token, card, Constant.LOGIN_VALID_MINUTE, TimeUnit.MINUTES);
				map.put("token", token);
				map.put("name", user.getName());
				result = PWD_SUCCESS;
			} else {
				log.info("card: {} 忘记密码失败，因为根据输入的学号和手机号无法匹配相应的学生！", card);
				result = NOT_EXIST;
			}
			map.put("result", result);
			return map;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("card: {} 忘记密码失败，因为发生了异常！", card);
			map.put("result", PWD_FAIL);
			return map;
		}
	}

	/**
	 * 修改密码
	 *
	 * @param card      编号
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
	 * @param checkCode 验证码
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
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
						QueryWrapper<User> queryWrapper = new QueryWrapper<>();
						queryWrapper.eq("card", card).eq("password", oldPwd);
						int flag = userMapper.selectCount(queryWrapper);
						if (flag == 1) {
							User user = userMapper.selectById(card);
							user.setPassword(newPwd);
							userMapper.updateById(user);
							map.put("result", PWD_SUCCESS);
						} else {
							// 原密码错误，导致成功执行条数不为1
							log.info("card: {} 修改密码失败，因为原密码输入错误！", card);
							map.put("result", PWD_ERR);
						}
					} else {
						// 验证码不正确
						log.info("card: {} 修改密码失败，因为验证码输入错误！", card);
						map.put("result", CODE_ERR);
					}
				} else {
					// 验证码已过期
					log.info("card: {} 修改密码失败，因为验证码已过期！", card);
					map.put("result", CODE_INVALID);
				}
			} else {
				// 登录状态失效
				log.info("card: {} 修改密码失败，因为登录状态失效！", card);
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("card: {} 修改密码失败，因为发生了异常！", card);
			map.put("result", DO_FAIL);
			return map;
		}
	}

	/**
	 * 更新用户信息
	 *
	 * @param name  姓名
	 * @param addr  地址
	 * @param mail  邮箱
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> updateInfo(String name, String addr, String mail, String token) {
		Map<String, String> map = new HashMap<>();
		try {
			if (stringRedisTemplate.hasKey(token)) {
				String card = stringRedisTemplate.opsForValue().get(token);
				User user = userMapper.selectById(card);
				user.setName(name);
				user.setAddr(addr);
				user.setMail(mail);
				userMapper.updateById(user);
				map.put("result", DO_SUCCESS);
			} else {
				// 登录状态失效
				log.info("token: {} 更新信息失败，因为登录状态失效！", token);
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("token: {} 更新信息失败，因为发生了异常！", token);
			map.put("result", DO_FAIL);
			return map;
		}
	}

}
