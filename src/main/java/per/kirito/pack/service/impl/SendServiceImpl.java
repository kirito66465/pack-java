package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.other.myEnum.Express;
import per.kirito.pack.other.myEnum.Status;
import per.kirito.pack.pojo.Send;
import per.kirito.pack.pojo.User;
import per.kirito.pack.pojo.utilPojo.SendRequest;
import per.kirito.pack.service.inter.SendService;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:53
 * @description:
 */
@Service
public class SendServiceImpl implements SendService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final String ZTO = String.valueOf(Express.中通);
	private static final String YD = String.valueOf(Express.韵达);
	private static final String EMS = String.valueOf(Express.EMS);

	private static final String ZTO_TYPE = Express.中通.getExpressEngName();
	private static final String YD_TYPE = Express.韵达.getExpressEngName();
	private static final String EMS_TYPE = Express.EMS.getExpressEngName();

	private static final String LOGIN_TO_DO = Status.LOGIN_TO_DO.getEnMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getEnMsg();

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> sendPack(SendRequest request, String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			try {
				Send send = new Send();
				String card = stringRedisTemplate.opsForValue().get(token);
				User user = userMapper.getUserById(card);
				// 设置寄件人信息
				send.setFrom_name(user.getName());
				send.setFrom_tel(user.getPhone());
				send.setFrom_addr(user.getAddr());
				// 设置收件人信息
				send.setTo_name(request.getName());
				send.setTo_tel(request.getPhone());
				send.setTo_addr(request.getAddr());
				// 生成快递单号
				String id = "";
			} catch (Exception e) {
				map.put("result", DO_FAIL);
				e.printStackTrace();
			}
			return map;
		} else {
			map.put("result", LOGIN_TO_DO);
			return map;
		}
	}

}
