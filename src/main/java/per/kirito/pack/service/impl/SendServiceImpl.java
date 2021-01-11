package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.kirito.pack.mapper.SendMapper;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.other.myEnum.Express;
import per.kirito.pack.other.myEnum.Status;
import per.kirito.pack.other.util.SendUtil;
import per.kirito.pack.other.util.TypeConversion;
import per.kirito.pack.pojo.Send;
import per.kirito.pack.pojo.User;
import per.kirito.pack.pojo.utilPojo.Page;
import per.kirito.pack.pojo.utilPojo.SendRequest;
import per.kirito.pack.service.inter.SendService;

import java.util.HashMap;
import java.util.List;
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
	private SendMapper sendMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final String LOGIN_TO_DO = Status.LOGIN_TO_DO.getEnMsg();
	private static final String DO_SUCCESS = Status.DO_SUCCESS.getEnMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getEnMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getEnMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getEnMsg();

	private static final String SEND_STATUS_0 = Status.SEND_STATUS_0.getEnMsg();        // 已提交
	private static final String SEND_STATUS_1 = Status.SEND_STATUS_1.getEnMsg();        // 已支付
	private static final String SEND_STATUS_2 = Status.SEND_STATUS_2.getEnMsg();        // 已确认
	private static final String SEND_STATUS_3 = Status.SEND_STATUS_3.getEnMsg();        // 已发出

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: User寄件下单
	 * @Param: [request]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> sendPack(SendRequest request) {
		Map<String, Object> map = new HashMap<>();
		String token = request.getToken();
		if (stringRedisTemplate.hasKey(token)) {
			try {
				Send send = new Send();
				String card = stringRedisTemplate.opsForValue().get(token);
				User user = userMapper.getUserById(card);
				// 设置寄件人信息
				send.setFrom_name(user.getName());
				send.setFrom_tel(user.getPhone());
				String postAddr = request.getAdmin();
				send.setFrom_addr("江苏省南京市栖霞区南京财经大学" + postAddr);
				// 设置收件人信息
				send.setTo_name(request.getName());
				send.setTo_tel(request.getPhone());
				String toAddr = request.getAddr();
				send.setTo_addr(toAddr);
				// 生成快递单号和快递公司
				String id = SendUtil.getSendIdAndOrg(postAddr).get("id");
				String org = SendUtil.getSendIdAndOrg(postAddr).get("org");
				send.setId(id);
				send.setOrg(org);
				send.setStatus(SEND_STATUS_0);
				String dt = TypeConversion.getTime();
				send.setDt(dt);
				send.setPrice(request.getPrice());
				// 向t_send表中插入这条数据
				sendMapper.addSend(send);
				map.put("result", DO_SUCCESS);
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

	/**
	 * @Description: User支付寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> sendPay(String id, String token) {
		Map<String, String> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			try {
				String dt = TypeConversion.getTime();
				sendMapper.updateSend(id, SEND_STATUS_1, dt);
				map.put("result", DO_SUCCESS);
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

	/**
	 * @Description: Admin确认寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> sendConfirm(String id, String token) {
		Map<String, String> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			try {
				String dt = TypeConversion.getTime();
				sendMapper.updateSend(id, SEND_STATUS_2, dt);
				map.put("result", DO_SUCCESS);
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

	/**
	 * @Description: Admin发出寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> sendOut(String id, String token) {
		Map<String, String> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			try {
				String dt = TypeConversion.getTime();
				sendMapper.updateSend(id, SEND_STATUS_3, dt);
				map.put("result", DO_SUCCESS);
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

	/**
	 * @Description: User取消寄件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, String> sendCancel(String id, String token) {
		Map<String, String> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			try {
				sendMapper.deleteSend(id);
				map.put("result", DO_SUCCESS);
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

	/**
	 * @Description: 分页方式获取User寄件集合
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	public Map<String, Object> getSendByUser(int currentPage, int pageSize, String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据card查询出该user寄件集合
			List<Send> sends = sendMapper.getSendByUser(card);
			if (sends != null) {
				// 获取分页方式结果集
				Page<Send> sendPage = SendUtil.getSendByPage(currentPage, pageSize, sends);
				map.put("result", sendPage);
			} else {
				map.put("result", NOT_EXIST);
			}
		} else {
			map.put("result", INFO_FAIL);
		}
		return map;
	}

	/**
	 * @Description: 获取User寄件数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	public Map<String, Object> getTotalByUser(String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			String card = stringRedisTemplate.opsForValue().get(token);
			int submit = sendMapper.getUserTotal(card, SEND_STATUS_0);
			int pay = sendMapper.getUserTotal(card, SEND_STATUS_2);
			int confirm = sendMapper.getUserTotal(card, SEND_STATUS_1);
			int out = sendMapper.getUserTotal(card, SEND_STATUS_3);
			map.put("sendSubmit", submit);
			map.put("sendPay", pay);
			map.put("sendConfirm", confirm);
			map.put("sendOut", out);
		} else {
			map.put("result", INFO_FAIL);
		}
		return map;
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页方式获取Admin寄件集合
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	public Map<String, Object> getSendByAdmin(int currentPage, int pageSize, String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 获取所在驿站的寄件快递种类/公司
			String card = stringRedisTemplate.opsForValue().get(token);
			String org = SendUtil.getSendOrg(card);
			// 根据org查询出该Admin寄件集合
			List<Send> sends = sendMapper.getSendByAdmin(org);
			if (sends != null) {
				// 获取分页方式结果集
				Page<Send> sendPage = SendUtil.getSendByPage(currentPage, pageSize, sends);
				map.put("result", sendPage);
			} else {
				map.put("result", NOT_EXIST);
			}
		} else {
			map.put("result", INFO_FAIL);
		}
		return map;
	}

	/**
	 * @Description: 获取Admin寄件数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	public Map<String, Object> getTotalByAdmin(String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 获取所在驿站的寄件快递种类/公司
			String card = stringRedisTemplate.opsForValue().get(token);
			String org = SendUtil.getSendOrg(card);
			int submit = sendMapper.getAdminTotal(org, SEND_STATUS_0);
			int pay = sendMapper.getAdminTotal(org, SEND_STATUS_2);
			int confirm = sendMapper.getAdminTotal(org, SEND_STATUS_1);
			int out = sendMapper.getAdminTotal(org, SEND_STATUS_3);
			map.put("sendSubmit", submit);
			map.put("sendPay", pay);
			map.put("sendConfirm", confirm);
			map.put("sendOut", out);
		} else {
			map.put("result", INFO_FAIL);
		}
		return map;
	}

}
