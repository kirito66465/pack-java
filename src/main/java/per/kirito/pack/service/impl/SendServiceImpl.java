package per.kirito.pack.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.kirito.pack.mapper.SendMapper;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.util.SendUtil;
import per.kirito.pack.util.TypeConversion;
import per.kirito.pack.pojo.Send;
import per.kirito.pack.pojo.User;
import per.kirito.pack.pojo.utilPojo.Page;
import per.kirito.pack.pojo.utilPojo.SendRequest;
import per.kirito.pack.service.inter.SendService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: 严晨
 * date: 2021/1/4
 * time: 15:53
 * 寄件 Send 的Service 层
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
	 * 流程相关
	 **/

	/**
	 * User 寄件下单
	 * @param request   寄件请求实体
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
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
				// 向 t_send 表中插入这条数据
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
	 * User 支付寄件
	 * @param id    学号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String sendPay(String id, String token) {
		if (!stringRedisTemplate.hasKey(token)) {
			return LOGIN_TO_DO;
		}
		try {
			String dt = TypeConversion.getTime();
			sendMapper.updateSend(id, SEND_STATUS_1, dt);
			return DO_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return DO_FAIL;
		}
	}

	/**
	 * Admin 确认寄件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String sendConfirm(String ids, String token) {
		if (!stringRedisTemplate.hasKey(token)) {
			return LOGIN_TO_DO;
		}
		try {
			if (!"".equals(ids)) {
				String[] idArr = ids.split(",");
				String dt = "";
				for (String id : idArr) {
					dt = TypeConversion.getTime();
					sendMapper.updateSend(id, SEND_STATUS_2, dt);
				}
				return DO_SUCCESS;
			} else {    // 传入的 ids 为空字符串时
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DO_FAIL;
		}
	}

	/**
	 * Admin 发出寄件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String sendOut(String ids, String token) {
		if (!stringRedisTemplate.hasKey(token)) {
			return LOGIN_TO_DO;
		}
		try {
			if (!"".equals(ids)) {
				String[] idArr = ids.split(",");
				String dt = "";
				for (String id : idArr) {
					dt = TypeConversion.getTime();
					sendMapper.updateSend(id, SEND_STATUS_3, dt);
				}
				return DO_SUCCESS;
			} else {    // 传入的 ids 为空字符串时
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DO_FAIL;
		}
	}

	/**
	 * User 取消寄件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String sendCancel(String ids, String token) {
		if (!stringRedisTemplate.hasKey(token)) {
			return LOGIN_TO_DO;
		}
		try {
			if ("".equals(ids)) {
				String[] idArr = ids.split(",");
				for (String id : idArr) {
					sendMapper.deleteSend(id);
				}
			}
			return DO_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return DO_FAIL;
		}
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 **/

	/**
	 * 分页方式获取 User 寄件集合
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:寄件状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@Override
	public Map<String, Object> getSendByUser(String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()){
			System.out.println("key为: " + obj + "值为: "+ mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String statusArray = String.valueOf(mapParams.get("status"));
		String status = TypeConversion.arrayToString(statusArray);

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 user 寄件集合
			List<Send> sends = sendMapper.getSendByUser(card, org, status);
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
	 * 获取 User 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@Override
	public Map<String, Object> getTotalByUser(String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			String card = stringRedisTemplate.opsForValue().get(token);
			int submit = sendMapper.getUserTotal(card, SEND_STATUS_0);
			int pay = sendMapper.getUserTotal(card, SEND_STATUS_1);
			int confirm = sendMapper.getUserTotal(card, SEND_STATUS_2);
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
	 * Admin 相关
	 **/

	/**
	 * 分页方式获取 Admin 寄件集合
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, status:寄件状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@Override
	public Map<String, Object> getSendByAdmin(String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()){
			System.out.println("key为: " + obj + "值为: "+ mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String statusArray = String.valueOf(mapParams.get("status"));
		String status = TypeConversion.arrayToString(statusArray);

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 获取所在驿站的寄件快递种类/公司
			String card = stringRedisTemplate.opsForValue().get(token);
			String org = SendUtil.getSendOrg(card);
			// 根据 org 查询出该 Admin 寄件集合
			List<Send> sends = sendMapper.getSendByAdmin(org, status);
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
	 * 获取 Admin 寄件数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@Override
	public Map<String, Object> getTotalByAdmin(String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 获取所在驿站的寄件快递种类/公司
			String card = stringRedisTemplate.opsForValue().get(token);
			String org = SendUtil.getSendOrg(card);
			int submit = sendMapper.getAdminTotal(org, SEND_STATUS_0);
			int pay = sendMapper.getAdminTotal(org, SEND_STATUS_1);
			int confirm = sendMapper.getAdminTotal(org, SEND_STATUS_2);
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
