package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.util.CheckCodeUtil;
import per.kirito.pack.service.inter.CheckCodeService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/8
 * @Time: 16:28
 * @description: 验证码的 Service 实现类
 */
@Service
public class CheckCodeServiceImpl implements CheckCodeService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getEnMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getEnMsg();
	private static final String LOGIN_TO_DO = Status.LOGIN_TO_DO.getEnMsg();

	/**
	 * @Description: 获取验证码字符串和图片
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Override
	public Map<String, String> getCheckCode(String token) {
		Map<String, String> map = new HashMap<>();
		try {
			if (stringRedisTemplate.hasKey(token)) {
				// 生成验证码
				String code = CheckCodeUtil.getStringRandom(4);
				// 生成验证码图片
				String pic = CheckCodeUtil.imageToBase64(120, 40, code);
				String tokenCode = token + "-code";
				// 判断Redis中有无存储
				if (stringRedisTemplate.hasKey(tokenCode)) {
					// 当请求新的验证码时，如果旧的验证码存在则删除
					stringRedisTemplate.delete(tokenCode);
				}
				// 将验证码存入Redis，并设置有效期2分钟
				stringRedisTemplate.opsForValue().set(tokenCode, code, 2, TimeUnit.MINUTES);
				map.put("codePic", pic);
				System.out.println("code: " + code);
				map.put("result", INFO_SUCCESS);
			} else {
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", INFO_FAIL);
			return map;
		}
	}

}
