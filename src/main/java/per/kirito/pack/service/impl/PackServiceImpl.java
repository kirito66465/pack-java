package per.kirito.pack.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.kirito.pack.annotation.Comment;
import per.kirito.pack.mapper.*;
import per.kirito.pack.myenum.PackStatusEnum;
import per.kirito.pack.myenum.Status;
import per.kirito.pack.util.PackUtil;
import per.kirito.pack.util.PickCodeUtil;
import per.kirito.pack.util.TypeConversion;
import per.kirito.pack.pojo.*;
import per.kirito.pack.service.inter.PackService;

import java.util.*;

/**
 * @author kirito
 * @date 2020/12/23
 * @time 15:36
 * Pack 的 Service 层，PackService 接口的实现类
 */
@Slf4j
@Service
public class PackServiceImpl implements PackService {

	@Autowired
	private PackMapper packMapper;

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CodeMapper codeMapper;

	@Autowired
	private EchartsMapper echartsMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// 取件码使用状态
	private static final int IS_USE = Status.IS_USE.getCode();
	// 快递状态
	private static final int USE_CODE = Status.IS_USE.getCode();
	// 取件码使用状态
	private static final int CODE_STATUS_1 = Status.CODE_STATUS_1.getCode();
	private static final int CODE_STATUS_0 = Status.CODE_STATUS_0.getCode();

	private static final String INTO_SUCCESS = Status.INTO_SUCCESS.getEnMsg();
	private static final String INTO_FAIL = Status.INTO_FAIL.getEnMsg();
	private static final String PICK_SUCCESS = Status.PICK_SUCCESS.getEnMsg();
	private static final String PICK_FAIL = Status.PICK_FAIL.getEnMsg();
	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getEnMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getEnMsg();
	private static final String DO_SUCCESS = Status.DO_SUCCESS.getEnMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getEnMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getEnMsg();
	private static final String TAKE_SUCCESS = Status.TAKE_SUCCESS.getEnMsg();
	private static final String LOGIN_TO_DO = Status.LOGIN_TO_DO.getEnMsg();

	// 驿站已有取件码的最大快递数量
	private static final int MAX_PACKS = 2400;

	/**
	 * 驿站管理员添加快递入站
	 *
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String addPack(String id, String token) {
		if (stringRedisTemplate.hasKey(token)) {
			try {
				// 随机获取一个用户，用于绑定快递的收件信息
				User user = userMapper.getUserByRand();
				Pack pack = new Pack();
				pack.setId(id);
				pack.setPerName(user.getName());
				pack.setPerTel(user.getPhone());
				pack.setPerAddr(user.getAddr());
				// 根据快递单号 id 获取驿站相关信息
				pack = PackUtil.addInfo(pack);
				String addr = pack.getAddr();
				// 取出目前驿站信息
				Admin admin = getAdminByAddr(addr);
				int count = admin.getCount();
				// 查询最大取件码有无被使用，值为1说明状态为未使用且释放时间为空字符串即从未被使用过
				QueryWrapper<Code> codeQueryWrapper = new QueryWrapper<>();
				codeQueryWrapper.eq("code", "20-6-20")
						.eq("addr", addr)
						.eq("status", 0)
						.eq("free", "");
				int isUse = codeMapper.selectCount(codeQueryWrapper);
				String code = "";
				// 驿站现存快递数量小于能有取件码的快递数量
				if (count < MAX_PACKS) {
					// 此入站快递能有取件码
					Code coder = new Code();
					pack.setStatus(PackStatusEnum.PACK_STATUS_1);
					// 判断取件码有无被使用
					if (isUse == USE_CODE) {
						// 最大取件码未被使用，此时按照该驿站该快递未入站之前的快递数量生成取件码
						code = PickCodeUtil.generate(count);
						coder.setFree("");
						coder.setAddr(addr);
					} else {
						// 最大取件码已被使用，根据已被使用的取件码释放先后赋予取件码
						coder = codeMapper.findCodeFreeMin(addr);
						code = coder.getCode();
					}
					coder.setCode(code);
					coder.setStatus(IS_USE);
					// 更新取件码信息
					codeQueryWrapper = new QueryWrapper<>();
					codeQueryWrapper.eq("code", code).eq("addr", addr);
					codeMapper.update(coder, codeQueryWrapper);
				} else {
					// 该快递没法有取件码
					pack.setStatus(PackStatusEnum.PACK_STATUS__1);
					code = "";
				}
				pack.setCode(code);
				packMapper.insert(pack);
				// 更新 Admin 的 count 数
				adminMapper.updateCountPlusByPackId(id);
				// 更新 User 的 count 数
				userMapper.updateCountPlusByPackId(id);
				return INTO_SUCCESS;
			} catch (Exception e) {
				log.error("error: {}", e.getMessage(), e);
				log.info("token: {} 添加快递入站失败，因为发生了异常！", token);
				// 有异常，返回入站失败
				return INTO_FAIL;
			}
		} else {
			log.info("token: {} 添加快递入站失败，因为登录状态失效！", token);
			return LOGIN_TO_DO;
		}
	}

	/**
	 * User 进行取件请求，仅传入快递单号和 token
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String pickById(String ids, String token) {
		String msg = PICK_FAIL;
		try {
			// 如果 token 令牌存在
			if (stringRedisTemplate.hasKey(token)) {
				String[] idArr = ids.split(",");
				int noExistCount = 0;
				for (String id : idArr) {
					Pack pack = packMapper.selectById(id);
					String card = stringRedisTemplate.opsForValue().get(token);
					User user = userMapper.selectById(card);
					String name = user.getName();
					if (pack != null) {
						String addr = pack.getAddr();
						String code = pack.getCode();
						// 更新管理员信息
						Admin admin = getAdminByAddr(addr);
						adminMapper.updateCountLessByPackId(pack.getId());
						// 更新用户信息
						userMapper.updateCountLessByPackId(pack.getId());
						pack.setStatus(PackStatusEnum.PACK_STATUS_0);
						String time = TypeConversion.getTime();
						pack.setEnd(time);
						// 更新签收人
						pack.setPick(name);
						// 更新包裹状态、取件时间
						packMapper.updateById(pack);
						// 更新取件码使用状态与释放时间
						QueryWrapper<Code> codeQueryWrapper = new QueryWrapper<>();
						codeQueryWrapper.eq("code", code).eq("addr", addr);
						Code coder = getCodeById(codeQueryWrapper);
						coder.setStatus(CODE_STATUS_0);
						coder.setFree(time);
						codeMapper.update(coder, codeQueryWrapper);
						int count = admin.getCount() - 1;
						if (count >= MAX_PACKS) {
							// 当前快递取出后，驿站剩余未取快递数大于等于2400，则需要为未有取件码的快递根据最早入站时间赋予取件码
							pack = packMapper.getPackByStartMin(addr);
							pack.setCode(code);
							pack.setStatus(PackStatusEnum.PACK_STATUS_1);
							packMapper.updateById(pack);
							// 重新设置该 code 为使用状态
							coder.setStatus(CODE_STATUS_1);
							codeMapper.update(coder, codeQueryWrapper);
						}

						// Echarts 统计
						String adminCard = admin.getCard();
						updateEcharts(adminCard);
					} else {
						// 该快递不存在
						noExistCount++;
					}
				}
				if (noExistCount == 0) {
					msg = PICK_SUCCESS;
				} else {
					msg = "有 " + noExistCount + " 件快递取件失败，因为不存在！";
					log.info("token: {} {}", token, msg);
				}
			} else {
				// 请登录再操作
				log.info("token: {} 学生取件失败，因为登录状态失效！", token);
				msg = LOGIN_TO_DO;
			}
			return msg;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("token: {} 学生取件失败，因为发生了异常！", token);
			// 取件失败
			return PICK_FAIL;
		}
	}

	/**
	 * User 进行取件，必须传入驿站地址和取件码
	 *
	 * @param addr  驿站地址
	 * @param code  取件码
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String pickPackByUser(String addr, String code, String token) {
		String msg = PICK_FAIL;
		try {
			// 如果 token 令牌存在
			if (stringRedisTemplate.hasKey(token)) {
				QueryWrapper<Pack> packQueryWrapper = new QueryWrapper<>();
				packQueryWrapper.eq("addr", addr).eq("code", code).eq("status", PackStatusEnum.PACK_STATUS_1);
				Pack pack = packMapper.selectOne(packQueryWrapper);
				String card = stringRedisTemplate.opsForValue().get(token);
				User user = userMapper.selectById(card);
				String name = user.getName();
				if (pack != null) {
					// 更新管理员信息
					Admin admin = getAdminByAddr(addr);
					adminMapper.updateCountLessByPackId(pack.getId());
					// 如果为本人签收
					if (pack.getPerTel().equals(user.getPhone())) {
						// 本人签收
						msg = PICK_SUCCESS;
					} else {
						// 他人代取
						QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
						userQueryWrapper.eq("phone", pack.getPerTel());
						user = userMapper.selectOne(userQueryWrapper);
						msg = TAKE_SUCCESS;
					}
					// 更新用户信息
					userMapper.updateCountLessByPackId(pack.getId());
					pack.setStatus(PackStatusEnum.PACK_STATUS_0);
					String time = TypeConversion.getTime();
					pack.setEnd(time);
					// 更新签收人
					pack.setPick(name);
					// 更新包裹状态、取件时间
					packMapper.updateById(pack);
					// 更新取件码使用状态与释放时间
					QueryWrapper<Code> codeQueryWrapper = new QueryWrapper<>();
					codeQueryWrapper.eq("code", code).eq("addr", addr);
					Code coder = getCodeById(codeQueryWrapper);
					coder.setStatus(CODE_STATUS_0);
					coder.setFree(time);
					codeMapper.update(coder, codeQueryWrapper);
					int count = admin.getCount() - 1;
					if (count >= MAX_PACKS) {
						// 当前快递取出后，驿站剩余未取快递数大于等于2400，则需要为未有取件码的快递根据最早入站时间赋予取件码
						pack = packMapper.getPackByStartMin(addr);
						pack.setCode(code);
						pack.setStatus(PackStatusEnum.PACK_STATUS_1);
						packMapper.updateById(pack);
						// 重新设置该 code 为使用状态
						coder.setStatus(CODE_STATUS_1);
						codeMapper.update(coder, codeQueryWrapper);
					}

					// Echarts 统计
					String adminCard = admin.getCard();
					updateEcharts(adminCard);
				} else {
					// 该快递不存在
					log.info("token: {} 学生取件失败，因为根据驿站地址和取件码，该快递不存在！", token);
					msg = NOT_EXIST;
				}
			} else {
				// 请登录再操作
				log.info("token: {} 学生取件失败，因为登录状态失效！", token);
				msg = LOGIN_TO_DO;
			}
			return msg;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("token: {} 学生取件失败，因为发生了异常！", token);
			// 取件失败
			return PICK_FAIL;
		}
	}

	/**
	 * Admin 进行取件，仅传入快递单号即可
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String pickPackByAdmin(String ids, String token) {
		try {
			if (!stringRedisTemplate.hasKey(token)) {
				return LOGIN_TO_DO;
			}
			String[] idArr = ids.split(",");
			int noExistCount = 0;
			for (String id : idArr) {
				Pack pack = packMapper.selectById(id);
				if (pack != null) {
					// 更新管理员信息
					String addr = pack.getAddr();
					Admin admin = getAdminByAddr(addr);
					adminMapper.updateCountLessByPackId(id);
					// 更新用户信息
					userMapper.updateCountLessByPackId(id);
					// 更新包裹状态、取件时间
					pack.setStatus(PackStatusEnum.PACK_STATUS_0);
					String time = TypeConversion.getTime();
					pack.setEnd(time);
					pack.setPick(admin.getName());
					packMapper.updateById(pack);
					int count = admin.getCount() - 1;
					// 更新取件码使用状态与释放时间
					String code = pack.getCode();
					if (code != null && !"".equals(code)) {
						QueryWrapper<Code> codeQueryWrapper = new QueryWrapper<>();
						codeQueryWrapper.eq("code", code).eq("addr", addr);
						Code coder = getCodeById(codeQueryWrapper);
						coder.setStatus(CODE_STATUS_0);
						coder.setFree(time);
						codeMapper.update(coder, codeQueryWrapper);
						if (count >= MAX_PACKS) {
							// 当前快递取出后，驿站剩余未取快递数大于等于2400，则需要为有取件码的快递根据最早入站时间赋予取件码
							pack = packMapper.getPackByStartMin(addr);
							pack.setCode(code);
							pack.setStatus(PackStatusEnum.PACK_STATUS_1);
							packMapper.updateById(pack);
							// 重新设置该 code 为使用状态
							coder.setStatus(CODE_STATUS_1);
							codeMapper.update(coder, codeQueryWrapper);
						}
					}

					// Echarts 统计
					String adminCard = admin.getCard();
					updateEcharts(adminCard);
				} else {
					noExistCount++;
				}
			}
			if (noExistCount == 0) {
				return PICK_SUCCESS;
			} else {
				String msg = "有 " + noExistCount + " 件快递取件失败，因为不存在！";
				log.info("token: {} {}", token, msg);
				return msg;
			}
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("token: {} 驿站管理员取件失败，因为发生了异常！", token);
			return PICK_FAIL;
		}
	}

	/**
	 * 根据快递单号删除此快递
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String deletePacksById(String ids, String token) {
		try {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				if (!stringRedisTemplate.hasKey(token)) {
					return LOGIN_TO_DO;
				}
				Pack pack = packMapper.selectById(id);
				PackStatusEnum status = pack.getStatus();
				// 已取快递不更新 Admin 与 User 的 count 值
				if (status != PackStatusEnum.PACK_STATUS_0) {
					// 更新 User 的 count 数
					userMapper.updateCountLessByPackId(id);
					// 更新 Admin 的 count 数
					adminMapper.updateCountLessByPackId(id);
				}
				// 删除快递
				packMapper.deleteById(id);
			}
			return DO_SUCCESS;
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("token: {} 删除快递失败，因为发生了异常！", token);
			return DO_FAIL;
		}
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 */

	/**
	 * 分页获取 User 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getUserPackByPage(
			@Comment(detail = {"currentPage:当前页", "pageSize:每页大小", "token:令牌", "org:快递公司", "addr:驿站地址", "status:快递状态", "search:搜索"})
					String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()) {
			log.info("key为: " + obj + "值为: " + mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String addrArray = String.valueOf(mapParams.get("addr"));
		String addr = TypeConversion.arrayToString(addrArray);
		String statusArray = String.valueOf(mapParams.get("status"));
		String statusStr = TypeConversion.arrayToString(statusArray);
		Integer[] status;
		if ("2".equals(statusStr)) {
			status = new Integer[]{2};
		} else {
			status = TypeConversion.stringToIntegerArray(statusStr);
		}
		String search = String.valueOf(mapParams.get("search"));

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 User 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 User 已取快递集合
			Page<Pack> page = new Page<>(currentPage, pageSize);
			Page<Pack> packs = packMapper.getUserPacks(page, card, org, addr, status, search);
			per.kirito.pack.pojo.utilpojo.Page<Pack> result = new per.kirito.pack.pojo.utilpojo.Page<Pack>();
			result.setCurrentPage(currentPage);
			result.setPageSize(pageSize);
			result.setTotal(packs.getTotal());
			result.setList(packs.getRecords());
			map.put("pack_result", result);
		} else {
			log.info("token: {} 获取学生快递集失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 分页获取 User 已取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getUserIsPick(
			@Comment(detail = {"currentPage:当前页", "pageSize:每页大小", "token:令牌", "org:快递公司", "search:搜索"})
					String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()) {
			log.info("key为: " + obj + "值为: " + mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String search = String.valueOf(mapParams.get("search"));

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 User 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 User 已取快递集合
			Page<Pack> page = new Page<>(currentPage, pageSize);
			Page<Pack> packs = packMapper.getUserIsPick(page, card, org, search);
			per.kirito.pack.pojo.utilpojo.Page<Pack> result = new per.kirito.pack.pojo.utilpojo.Page<Pack>();
			result.setCurrentPage(currentPage);
			result.setPageSize(pageSize);
			result.setTotal(packs.getTotal());
			result.setList(packs.getRecords());
			map.put("pack_result", result);
		} else {
			log.info("token: {} 获取学生已取快递集失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 分页获取 User 所未取出的快递， 无论有无取件码；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getUserNoPick(
			@Comment(detail = {"currentPage:当前页", "pageSize:每页大小", "token:令牌", "org:快递公司", "addr:驿站地址", "status:快递状态", "search:搜索"})
					String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()) {
			log.info("key为: " + obj + "值为: " + mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String addrArray = String.valueOf(mapParams.get("addr"));
		String addr = TypeConversion.arrayToString(addrArray);
		String statusArray = String.valueOf(mapParams.get("status"));
		String statusStr = TypeConversion.arrayToString(statusArray);
		Integer[] status;
		if ("2".equals(statusStr)) {
			status = new Integer[]{2};
		} else {
			status = TypeConversion.stringToIntegerArray(statusStr);
		}
		String search = String.valueOf(mapParams.get("search"));

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 User 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 User 已取快递集合
			Page<Pack> page = new Page<>(currentPage, pageSize);
			Page<Pack> packs = packMapper.getUserNoPick(page, card, org, addr, status, search);
			per.kirito.pack.pojo.utilpojo.Page<Pack> result = new per.kirito.pack.pojo.utilpojo.Page<Pack>();
			result.setCurrentPage(currentPage);
			result.setPageSize(pageSize);
			result.setTotal(packs.getTotal());
			result.setList(packs.getRecords());
			map.put("pack_result", result);
		} else {
			log.info("token: {} 获取学生未取快递集失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 获取 User 所有快递总数、已取快递数量、未取快递数量
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getUserTotalNum(String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出用户手机号
			String card = stringRedisTemplate.opsForValue().get(token);
			User user = userMapper.selectById(card);
			String phone = user.getPhone();
			int allTotal = packMapper.getUserAllTotalNum(phone);
			int isTotal = packMapper.getUserIsTotalNum(phone);
			int noTotal = packMapper.getUserNoTotalNum(phone);
			map.put("allTotal", allTotal);
			map.put("isTotal", isTotal);
			map.put("noTotal", noTotal);
			map.put("result", INFO_SUCCESS);
		} else {
			log.info("token: {} 获取学生快递数量失败，因为登录状态失效！", token);
			map.put("result", INFO_FAIL);
		}
		return map;
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 */

	/**
	 * 分页获取 Admin 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getAdminPackByPage(
			@Comment(detail = {"currentPage:当前页", "pageSize:每页大小", "token:令牌", "org:快递公司", "status:快递状态", "search:搜索"})
					String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()) {
			log.info("key为: " + obj + "值为: " + mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String statusArray = String.valueOf(mapParams.get("status"));
		String statusStr = TypeConversion.arrayToString(statusArray);
		Integer[] status;
		if ("2".equals(statusStr)) {
			status = new Integer[]{2};
		} else {
			status = TypeConversion.stringToIntegerArray(statusStr);
		}
		String search = String.valueOf(mapParams.get("search"));

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 Admin 所有快递集合
			Page<Pack> page = new Page<>(currentPage, pageSize);
			Page<Pack> packs = packMapper.getAdminPacks(page, card, org, status, search);
			per.kirito.pack.pojo.utilpojo.Page<Pack> result = new per.kirito.pack.pojo.utilpojo.Page<Pack>();
			result.setCurrentPage(currentPage);
			result.setPageSize(pageSize);
			result.setTotal(packs.getTotal());
			result.setList(packs.getRecords());
			map.put("pack_result", result);
		} else {
			log.info("token: {} 获取驿站管理员快递集失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 分页获取当前驿站的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getAdminIsPick(
			@Comment(detail = {"currentPage:当前页", "pageSize:每页大小", "token:令牌", "org:快递公司", "search:搜索"})
					String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()) {
			log.info("key为: " + obj + "值为: " + mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String search = String.valueOf(mapParams.get("search"));

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 Admin 已取快递集合
			Page<Pack> page = new Page<>(currentPage, pageSize);
			Page<Pack> packs = packMapper.getAdminIsPick(page, card, org, search);
			per.kirito.pack.pojo.utilpojo.Page<Pack> result = new per.kirito.pack.pojo.utilpojo.Page<Pack>();
			result.setCurrentPage(currentPage);
			result.setPageSize(pageSize);
			result.setTotal(packs.getTotal());
			result.setList(packs.getRecords());
			map.put("pack_result", result);
		} else {
			log.info("token: {} 获取驿站管理员已取快递集失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 分页获取当前驿站的未取出快递，无论有无取件码；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json      参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getAdminNoPick(
			@Comment(detail = {"currentPage:当前页", "pageSize:每页大小", "token:令牌", "org:快递公司", "status:快递状态", "search:搜索"})
					String json) {
		Map mapTypes = JSON.parseObject(json);
		Map<String, Object> mapParams = new HashMap<>();
		for (Object obj : mapTypes.keySet()) {
			log.info("key为: " + obj + "值为: " + mapTypes.get(obj));
			mapParams.put(String.valueOf(obj), mapTypes.get(obj));
		}
		int currentPage = (int) mapParams.get("currentPage");
		int pageSize = (int) mapParams.get("pageSize");
		String token = String.valueOf(mapParams.get("token"));
		String orgArray = String.valueOf(mapParams.get("org"));
		String org = TypeConversion.arrayToString(orgArray);
		String statusArray = String.valueOf(mapParams.get("status"));
		String statusStr = TypeConversion.arrayToString(statusArray);
		Integer[] status;
		if ("2".equals(statusStr)) {
			status = new Integer[]{2};
		} else {
			status = TypeConversion.stringToIntegerArray(statusStr);
		}
		String search = String.valueOf(mapParams.get("search"));

		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 Admin 未取快递集合
			Page<Pack> page = new Page<>(currentPage, pageSize);
			Page<Pack> packs = packMapper.getAdminNoPick(page, card, org, status, search);
			per.kirito.pack.pojo.utilpojo.Page<Pack> result = new per.kirito.pack.pojo.utilpojo.Page<Pack>();
			result.setCurrentPage(currentPage);
			result.setPageSize(pageSize);
			result.setTotal(packs.getTotal());
			result.setList(packs.getRecords());
			map.put("pack_result", result);
		} else {
			log.info("token: {} 获取驿站管理员未取快递集失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 获取 Admin 所有快递总数、已取快递数量、未取快递数量
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getAdminTotalNum(String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出驿站地址
			String card = stringRedisTemplate.opsForValue().get(token);
			Admin admin = adminMapper.selectById(card);
			String addr = admin.getAddr();
			int allTotal = packMapper.getAdminAllTotalNum(addr);
			int isTotal = packMapper.getAdminIsTotalNum(addr);
			int noTotal = packMapper.getAdminNoTotalNum(addr);
			float percentage = 100;
			if (noTotal < MAX_PACKS) {
				percentage = noTotal / (float) MAX_PACKS * 100;
				if (percentage < 1) {
					percentage = 1;
				}
			}
			map.put("percentage", percentage);
			map.put("allTotal", allTotal);
			map.put("isTotal", isTotal);
			map.put("noTotal", noTotal);
			map.put("result", INFO_SUCCESS);
		} else {
			log.info("token: {} 获取驿站管理员快递数量失败，因为登录状态失效！", token);
			map.put("result", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 根据驿站地址和货架获取当前货架的所有快递
	 *
	 * @param token 令牌
	 * @param shelf 货架
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getShelfPack(String token, String shelf) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			String card = stringRedisTemplate.opsForValue().get(token);
			List<Pack> packs = packMapper.getShelfPack(card, shelf);
			map.put("packs", packs);
			map.put("result", INFO_SUCCESS);
		} else {
			log.info("token: {} 根据货架获取驿站管理员快递集失败，因为登录状态失效！", token);
			map.put("result", INFO_FAIL);
		}
		return map;
	}

	/**
	 * 获取不筛选不分页的驿站所有快递集合，以便生成 Excel
	 *
	 * @param token 令牌
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	@Override
	public List<Pack> getAllPacksByExcelOfAdmin(String token) {
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 Admin 所有快递集合
			List<Pack> packs = packMapper.getAllPacksByExcelOfAdmin(card);
			return packs;
		}
		return null;
	}

	/**
	 * 获取不筛选不分页的驿站已取快递集合，以便生成 Excel
	 *
	 * @param token 令牌
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	@Override
	public List<Pack> getIsPacksByExcelOfAdmin(String token) {
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 Admin 已取快递集合
			List<Pack> packs = packMapper.getIsPacksByExcelOfAdmin(card);
			return packs;
		}
		return null;
	}

	/**
	 * 获取不筛选不分页的驿站未取快递集合，以便生成 Excel
	 *
	 * @param token 令牌
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	@Override
	public List<Pack> getNoPacksByExcelOfAdmin(String token) {
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			// 根据 card 查询出该 Admin 未取快递集合
			List<Pack> packs = packMapper.getNoPacksByExcelOfAdmin(card);
			return packs;
		}
		return null;
	}

	/**
	 * 更新 Echarts 数据
	 *
	 * @param card 时间
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateEcharts(String card) {
		String date = DateUtil.today();
		QueryWrapper<Echarts> echartsQueryWrapper = new QueryWrapper<>();
		echartsQueryWrapper.eq("datee", date).eq("card", card);
		Echarts echarts = echartsMapper.selectOne(echartsQueryWrapper);
		if (echarts == null) {
			echarts = new Echarts();
			echarts.setDatee(date);
			echarts.setCard(card);
			echarts.setNine(0);
			echarts.setTen(0);
			echarts.setEleven(0);
			echarts.setTwelve(0);
			echarts.setThirteen(0);
			echarts.setFourteen(0);
			echarts.setFifteen(0);
			echarts.setSixteen(0);
			echarts.setSeventeen(0);
			echarts.setEighteen(0);
			echarts.setNineteen(0);
		}
		Integer hour = getHour();
		int count = 0;
		switch (hour) {
			case 9:
				count = echarts.getNine();
				echarts.setNine(count + 1);
				break;
			case 10:
				count = echarts.getTen();
				echarts.setTen(count + 1);
				break;
			case 11:
				count = echarts.getEleven();
				echarts.setEleven(count + 1);
				break;
			case 12:
				count = echarts.getTwelve();
				echarts.setTwelve(count + 1);
				break;
			case 13:
				count = echarts.getThirteen();
				echarts.setThirteen(count + 1);
				break;
			case 14:
				count = echarts.getFourteen();
				echarts.setFourteen(count + 1);
				break;
			case 15:
				count = echarts.getFifteen();
				echarts.setFifteen(count + 1);
				break;
			case 16:
				count = echarts.getSixteen();
				echarts.setSixteen(count + 1);
				break;
			case 17:
				count = echarts.getSeventeen();
				echarts.setSeventeen(count + 1);
				break;
			case 18:
				count = echarts.getEighteen();
				echarts.setEighteen(count + 1);
				break;
			case 19:
				count = echarts.getNineteen();
				echarts.setNineteen(count + 1);
				break;
			default:
				break;
		}
		try {
			echartsMapper.update(echarts, echartsQueryWrapper);
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("adminCard: {} 更新 Echarts 表失败，因为发生了异常！", card);
		}
	}

	/**
	 * 获取所需范围的小时值（9-19）
	 *
	 * @return java.lang.Integer
	 */
	private Integer getHour() {
		int now = DateUtil.hour(DateUtil.date(), true);
		int hour;
		if (now < 9) {
			hour = 9;
		} else if (now > 19) {
			hour = 19;
		} else {
			hour = now;
		}
		return hour;
	}

	/**
	 * 根据驿站地址获取驿站管理员信息
	 *
	 * @param addr 驿站地址
	 * @return per.kirito.pack.pojo.Admin
	 */
	private Admin getAdminByAddr(String addr) {
		QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
		adminQueryWrapper.eq("addr", addr);
		Admin admin = adminMapper.selectOne(adminQueryWrapper);
		return admin;
	}

	/**
	 * 根据取件码和驿站地址获取该取件码信息
	 *
	 * @param codeQueryWrapper 条件构造器
	 * @return per.kirito.pack.pojo.Code
	 */
	public Code getCodeById(QueryWrapper<Code> codeQueryWrapper) {
		return codeMapper.selectOne(codeQueryWrapper);
	}

}
