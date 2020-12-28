package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import per.kirito.pack.mapper.AdminMapper;
import per.kirito.pack.mapper.CodeMapper;
import per.kirito.pack.mapper.PackMapper;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.other.myEnum.Express;
import per.kirito.pack.other.myEnum.Status;
import per.kirito.pack.other.util.PackUtil;
import per.kirito.pack.other.util.TypeConversion;
import per.kirito.pack.pojo.*;
import per.kirito.pack.service.inter.PackService;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:36
 * @description:
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

	private static Code MAX_CODE_UNUSED = new Code("20-6-20", "", 0);

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

	private static final String INTO_SUCCESS = Status.INTO_SUCCESS.getMsg();
	private static final String INTO_FAIL = Status.INTO_FAIL.getMsg();
	private static final String PICK_SUCCESS = Status.PICK_SUCCESS.getMsg();
	private static final String PICK_FAIL = Status.PICK_FAIL.getMsg();
	private static final String INFO_SUCCESS = Status.INFO_SUCCESS.getMsg();
	private static final String INFO_FAIL = Status.INFO_FAIL.getMsg();
	private static final String DO_SUCCESS = Status.DO_SUCCESS.getMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getMsg();

	@Override
	public Object getPackById(String id) {
		Pack pack = packMapper.getPackById(id);
		if (pack == null) {
			return INFO_FAIL;
		} else {
			return pack;
		}
	}

	@Override
	public String addPack(String id) {
		// 取出登录的管理员的编号、驿站地址
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		String addr = stringRedisTemplate.opsForValue().get("admin-addr");
		Pack pack = new Pack();
		pack.setId(id);
		Admin admin = adminMapper.getAdminById(card);
		int count = admin.getCount();
		// 查询最大取件码有无被使用
		MAX_CODE_UNUSED.setAddr(addr);
		// TODO: 添加取件码
		int isUse = codeMapper.findCode(MAX_CODE_UNUSED);
		// 判断取件码有无被使用
		if (isUse == NOT_USE) {
			// 最大取件码未被使用，此时按照取件码的排列顺序赋予取件码
		} else {
			// 最大取件码已被使用，根据已被使用的取件码释放先后赋予取件码
		}
		pack = PackUtil.addCode(pack, count);
		int flag = packMapper.addPack(pack);
		if (flag == INFO_CODE) {
			count = count + 1;
			admin.setCount(count);
			flag = adminMapper.updateAdmin(admin);
			if (flag == DO_CODE) {
				return INTO_SUCCESS;
			} else {
				return DO_FAIL;
			}
		} else {
			return INFO_FAIL;
		}
	}

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
				pack.setStatus(PICK_CODE);
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

	@Override
	public List<Pack> getPacksByUser(String card) {
		User user = userMapper.getUserById(card);
		String phone = user.getPhone();
		List<Pack> packs = packMapper.getPackSByPhone(phone);
		return packs;
	}

	@Override
	public List<Pack> getPacksByAdmin(String card) {
		Admin admin = adminMapper.getAdminById(card);
		String addr = admin.getAddr();
		List<Pack> packs = packMapper.getPacksByAddr(addr);
		return packs;
	}

	@Override
	public Page<Pack> getAdminPackByPage(int currentPage, int pageSize) {
		Page<Pack> page = new Page<>();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		String addr = stringRedisTemplate.opsForValue().get("admin-addr");
		int total = packMapper.getTotalByAddr(addr);
		List<Pack> packs = packMapper.getPacksByAddr(addr);
		page.setList(packs);
		return page;
	}
}
