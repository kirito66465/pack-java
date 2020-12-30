package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import per.kirito.pack.mapper.AdminMapper;
import per.kirito.pack.mapper.CodeMapper;
import per.kirito.pack.mapper.PackMapper;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.other.myEnum.Express;
import per.kirito.pack.other.myEnum.Status;
import per.kirito.pack.other.util.PackUtil;
import per.kirito.pack.other.util.PickCodeUtil;
import per.kirito.pack.other.util.TypeConversion;
import per.kirito.pack.pojo.*;
import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;
import per.kirito.pack.service.inter.PackService;

import java.util.*;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:36
 * @description: Pack的Service层，PackService接口的实现类
 */
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
	private StringRedisTemplate stringRedisTemplate;

	private static final String ZTO = String.valueOf(Express.中通);
	private static final String STO = String.valueOf(Express.申通);
	private static final String YTO = String.valueOf(Express.圆通);
	private static final String JD = String.valueOf(Express.京东);
	private static final String SF = String.valueOf(Express.顺丰);
	private static final String YD = String.valueOf(Express.韵达);
	private static final String TT = String.valueOf(Express.天天);
	private static final String EMS = String.valueOf(Express.EMS);

	private static final int INTO_CODE = Status.INTO_SUCCESS.getCode();
	private static final int PICK_CODE = Status.PICK_SUCCESS.getCode();
	private static final int INFO_CODE = Status.INFO_SUCCESS.getCode();
	private static final int DO_CODE = Status.DO_SUCCESS.getCode();
	private static final int IS_USE = Status.IS_USE.getCode();
	private static final int NOT_USE = Status.NOT_USE.getCode();
	private static final int PACK_CODE_1 = Status.PACK_STATUS_1.getCode();
	private static final int PACK_CODE_0 = Status.PACK_STATUS_0.getCode();
	private static final int PACK_CODE__1 = Status.PACK_STATUS__1.getCode();

	private static final String INTO_SUCCESS = Status.INTO_SUCCESS.getEnMsg();
	private static final String INTO_FAIL = Status.INTO_FAIL.getEnMsg();
	private static final String PICK_SUCCESS = Status.PICK_SUCCESS.getEnMsg();
	private static final String PICK_FAIL = Status.PICK_FAIL.getEnMsg();
	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getEnMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getEnMsg();
	private static final String DO_SUCCESS = Status.DO_SUCCESS.getEnMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getEnMsg();

	private static final String PACK_STATUS_1 = Status.PACK_STATUS_1.getZhMsg();
	private static final String PACK_STATUS_0 = Status.PACK_STATUS_0.getZhMsg();
	private static final String PACK_STATUS__1 = Status.PACK_STATUS__1.getZhMsg();

	// 驿站已有取件码的最大快递数量
	private static final int MAX_PACKS = 2400;

	private static Code MAX_CODE_UNUSED = new Code("20-6-20", "", NOT_USE);
	/**
	 * @Description: 根据快递单号获取快递信息，如果查询不出则返回String
	 * @Param: [id]
	 * @Return: java.lang.Object
	 **/
	@Override
	public Object getPackById(String id) {
		Pack pack = packMapper.getPackById(id);
		if (pack == null) {
			// 查询不出则返回"get info fail"
			return INFO_FAIL;
		} else {
			return pack;
		}
	}

	/**
	 * @Description: 驿站管理员添加快递入站
	 * @Param: [id]
	 * @Return: java.lang.String
	 **/
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String addPack(Pack pack) {          // 已有快递单号、收件人信息
		try {
			// 根据快递单号id获取驿站相关信息
			pack = PackUtil.addCode(pack);
			String addr = pack.getAddr();
			String phone = pack.getPer_tel();
			// 取出目前驿站信息
			Admin admin = adminMapper.getAdminByAddr(addr);
			int count = admin.getCount();
			// 驿站现存快递数量大于能有取件码的快递数量
			if (count >= MAX_PACKS) {
				pack.setStatus(PACK_CODE__1);
			} else {
				// 此入站快递能有取件码
				pack.setStatus(PACK_CODE_1);
			}
			// 查询最大取件码有无被使用
			MAX_CODE_UNUSED.setAddr(addr);
			int isUse = codeMapper.findCode(MAX_CODE_UNUSED);
			// 判断取件码有无被使用
			if (isUse == NOT_USE) {
				// 最大取件码未被使用，此时按照取件码的排列顺序赋予取件码
				String code = PickCodeUtil.generate(count);
				pack.setCode(code);
			} else {
				// 最大取件码已被使用，根据已被使用的取件码释放先后赋予取件码




			}
			packMapper.addPack(pack);
			count = count + 1;
			admin.setCount(count);
			// 更新Admin的count数
			adminMapper.updateAdmin(admin);
			// 更新User的count数
			User user = userMapper.getUserByPhone(phone);
			count = user.getCount() + 1;
			user.setCount(count);
			userMapper.updateUser(user);
			return INTO_SUCCESS;
		} catch (Exception e) {
			// 有异常，返回入站失败
			e.printStackTrace();
			return INTO_FAIL;
		}
	}

	/**
	 * @Description: User或Admin进行取件
	 * @Param: [id, code]
	 * @Return: java.lang.String
	 **/
	@Override
	public String pickPack(@RequestParam(value = "id") String id,
	                       @RequestParam(value = "code") String code) {
		String msg = "";
		int flag = DO_CODE;
		Pack pack = packMapper.getPackById(id);
		// 查询不到包裹信息
		if (pack == null) {
			msg = INFO_FAIL;
		} else {
			// 如果取件码正确，则取件
			if (code.equals(pack.getCode())) {
				// 更新管理员信息
				if (stringRedisTemplate.hasKey("admin-card")) {
					// 如果此时是管理员登录进行取件
					String adminCard = stringRedisTemplate.opsForValue().get("admin-card");
					Admin admin = adminMapper.getAdminById(adminCard);
					int count = admin.getCount() - 1;
					admin.setCount(count);
					flag = adminMapper.updateAdmin(admin);
				} else {
					// 如果此时是用户登录取件
					String admin_addr = pack.getAddr();
					Admin admin = adminMapper.getAdminByAddr(admin_addr);
					int count = admin.getCount() - 1;
					admin.setCount(count);
					flag = adminMapper.updateAdmin(admin);
				}
				// 更新用户信息
				if (stringRedisTemplate.hasKey("user-card")) {
					// 如果此时是用户登录进行取件
					String userCard = stringRedisTemplate.opsForValue().get("user-card");
					User user = userMapper.getUserById(userCard);
					int count = user.getCount() - 1;
					user.setCount(count);
					flag = userMapper.updateUser(user);
				} else {
					// 如果此时是管理员进行取件
					String userPhone = pack.getPer_tel();
					User user = userMapper.getUserByPhone(userPhone);
					// 判断该快递的手机号有无对应的学生用户存在
					if (user != null) {
						int count = user.getCount() - 1;
						user.setCount(count);
						flag = userMapper.updateUser(user);
					}
				}
				// 如果用户或管理学信息更新失败，则直接返回操作失败
				if (flag != DO_CODE) {
					msg = DO_FAIL;
					return msg;
				}
				pack.setStatus(PACK_CODE_0);
				String time = TypeConversion.getTime();
				pack.setEnd(time);
				// 更新包裹状态、取件时间
				flag = packMapper.updatePack(pack);
				if (flag == PICK_CODE) {
					msg = PICK_SUCCESS;
				} else {
					msg = DO_FAIL;
				}
			} else {
				// 如果取件码不正确，则不能取件
				msg = PICK_FAIL;
			}
		}
		return msg;
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * 抽取出来的方法
	 **/

	/**
	 * @Description: 抽取出来的获取结果集方法，主要对status进行类型转换：jdbcType/Integer -> java/String
	 * @Param: [packList]
	 * @Return: java.util.List<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	public static List<PackResult> getPackResult(List<Pack> packList) {
		List<PackResult> packResultList = new ArrayList<>();
		PackResult packResult;
		for (Pack pack : packList) {
			packResult = new PackResult(pack);
			packResultList.add(packResult);
		}
		return packResultList;
	}

	/**
	 * @Description: 抽取出来的分页方法，仅需传入当前页码、每页条数、快递结果集
	 * @Param: [currentPage, pageSize, packResultList]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	public static Page<PackResult> getPackByPage(int currentPage, int pageSize, List<PackResult> packResultList) {
		Page<PackResult> resultPage = new Page<>();
		resultPage.setCurrentPage(currentPage);
		resultPage.setPageSize(pageSize);
		List<PackResult> resultList = new ArrayList<>();
		PackResult packResult;
		int index = (currentPage - 1) * pageSize;
		int end = currentPage * pageSize;
		int size = packResultList.size();
		// 当最后一页记录条数不足每页记录条数时，end置为结果集的长度
		if (end > size) {
			end = size;
		}
		// 遍历结果集，获取到当前页数下的数据集
		for (int i = index; i < end; i++) {
			packResult = packResultList.get(i);
			resultList.add(packResult);
		}
		resultPage.setList(resultList);
		// 获取结果集数量
		int total = packResultList.size();
		resultPage.setTotal(total);
		return resultPage;
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: 分页获取User所有的快递，包括已取出和未取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@Override
	public Page<PackResult> getUserPackByPage(int currentPage, int pageSize) {
		// 取出User登录的card
		String card = stringRedisTemplate.opsForValue().get("user-card");
		// 根据card查询出该User已取快递集合
		List<Pack> packs = packMapper.getUserPacks(card);
		List<PackResult> packResultList = getPackResult(packs);
		// 获取分页方式的结果集
		Page<PackResult> resultPage = getPackByPage(currentPage, pageSize, packResultList);
		return resultPage;
	}

	/**
	 * @Description: 分页获取User已取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@Override
	public Page<PackResult> getUserIsPick(int currentPage, int pageSize) {
		// 取出User登录的card
		String card = stringRedisTemplate.opsForValue().get("user-card");
		// 根据card查询出该User已取快递集合
		List<Pack> packs = packMapper.getUserIsPick(card);
		List<PackResult> packResultList = getPackResult(packs);
		// 获取分页方式的结果集
		Page<PackResult> resultPage = getPackByPage(currentPage, pageSize, packResultList);
		return resultPage;
	}

	/**
	 * @Description: 分页获取User所未取出的快递， 无论有无取件码
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@Override
	public Page<PackResult> getUserNoPick(int currentPage, int pageSize) {
		// 取出User登录的card
		String card = stringRedisTemplate.opsForValue().get("user-card");
		// 根据card查询出该User已取快递集合
		List<Pack> packs = packMapper.getUserNoPick(card);
		List<PackResult> packResultList = getPackResult(packs);
		// 获取分页方式的结果集
		Page<PackResult> resultPage = getPackByPage(currentPage, pageSize, packResultList);
		return resultPage;
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页获取Admin所有的快递，包括已取出和未取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@Override
	public Page<PackResult> getAdminPackByPage(int currentPage, int pageSize) {
		// 取出Admin登录的card
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		// 根据card查询出该Admin已取快递集合
		List<Pack> packs = packMapper.getAdminPacks(card);
		List<PackResult> packResultList = getPackResult(packs);
		// 获取分页方式的结果集
		Page<PackResult> resultPage = getPackByPage(currentPage, pageSize, packResultList);
		return resultPage;
	}

	/**
	 * @Description: 分页获取当前驿站的已取出快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@Override
	public Page<PackResult> getAdminIsPick(int currentPage, int pageSize) {
		// 取出Admin登录的card
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		// 根据card查询出该Admin已取快递集合
		List<Pack> packs = packMapper.getAdminIsPick(card);
		List<PackResult> packResultList = getPackResult(packs);
		// 获取分页方式的结果集
		Page<PackResult> resultPage = getPackByPage(currentPage, pageSize, packResultList);
		return resultPage;
	}

	/**
	 * @Description: 分页获取当前驿站的未取出快递，无论有无取件码
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@Override
	public Page<PackResult> getAdminNoPick(int currentPage, int pageSize) {
		// 取出Admin登录的card
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		// 根据card查询出该Admin未取快递集合
		List<Pack> packs = packMapper.getAdminNoPick(card);
		List<PackResult> packResultList = getPackResult(packs);
		// 获取分页方式结果集
		Page<PackResult> resultPage = getPackByPage(currentPage, pageSize, packResultList);
		return resultPage;
	}

}
