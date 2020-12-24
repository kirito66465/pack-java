package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.myEnum.Express;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.pojo.Code;
import per.kirito.pack.pojo.Pack;
import per.kirito.pack.pojo.User;
import per.kirito.pack.service.inter.AdminService;
import per.kirito.pack.service.inter.CodeService;
import per.kirito.pack.service.inter.PackService;
import per.kirito.pack.service.inter.UserService;
import per.kirito.pack.util.PackUtil;
import per.kirito.pack.util.TypeConversion;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:19
 * @description: 包裹相关controller
 */
@RestController(value = "/pack")
public class PackController {
	@Autowired
	private PackService packService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private CodeService codeService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

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

	// 根据快递单号获取快递信息
	@CrossOrigin
	@RequestMapping(value = "/getPackById")
	public Object getPackById(@RequestParam(value = "id") String id) {
		Pack pack = packService.getPackById(id);
		if (pack == null) {
			return INFO_FAIL;
		} else {
			return pack;
		}
	}

	// 管理员添加快递入站
	@CrossOrigin
	@RequestMapping(value = "/addPack")
	public String addPack(@RequestParam(value = "id") String id) {           // TODO: 改成添加参数，其余自动生成
		// 取出登录的管理员的编号、驿站地址
		String card = stringRedisTemplate.opsForValue().get("admin-card");
		String addr = stringRedisTemplate.opsForValue().get("admin-addr");
		Pack pack = new Pack();
		pack.setId(id);
		Admin admin = adminService.getAdminById(card);
		int count = admin.getCount();
		// 查询最大取件码有无被使用
		MAX_CODE_UNUSED.setAddr(addr);
		// TODO: 添加取件码
		int isUse = codeService.findCode(MAX_CODE_UNUSED);
		// 判断取件码有无被使用
		if (isUse == NOT_USE) {
			// 最大取件码未被使用，此时按照取件码的排列顺序赋予取件码
		} else {
			// 最大取件码已被使用，根据已被使用的取件码释放先后赋予取件码
		}



		pack = PackUtil.addCode(pack, count);
		int flag = packService.addPack(pack);
		if (flag == INFO_CODE) {
			count = count + 1;
			admin.setCount(count);
			flag = adminService.updateAdmin(admin);
			if (flag == DO_CODE) {
				return INTO_SUCCESS;
			} else {
				return DO_FAIL;
			}
		} else {
			return INFO_FAIL;
		}
	}

	// 取件
	@CrossOrigin
	@RequestMapping(value = "/pickPack")
	public String pickPack(@RequestParam(value = "id") String id,
	                       @RequestParam(value = "code") String code) {
		String msg = "";
		int flag = DO_CODE;
		Pack pack = packService.getPackById(id);
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
					Admin admin = adminService.getAdminById(adminCard);
					int count = admin.getCount() - 1;
					admin.setCount(count);
					flag = adminService.updateAdmin(admin);
				} else {
					// 如果此时是用户登录取件
					String admin_addr = pack.getAddr();
					Admin admin = adminService.getAdminByAddr(admin_addr);
					int count = admin.getCount() - 1;
					admin.setCount(count);
					flag = adminService.updateAdmin(admin);
				}
				// 更新用户信息
				if (stringRedisTemplate.hasKey("user-card")) {
					// 如果此时是用户登录进行取件
					String userCard = stringRedisTemplate.opsForValue().get("user-card");
					User user = userService.getUserById(userCard);
					int count = user.getCount() - 1;
					user.setCount(count);
					flag = userService.updateUser(user);
				} else {
					// 如果此时是管理员进行取件
					String userPhone = pack.getPer_tel();
					User user = userService.getUserByPhone(userPhone);
					// 判断该快递的手机号有无对应的学生用户存在
					if (user != null) {
						int count = user.getCount() - 1;
						user.setCount(count);
						flag = userService.updateUser(user);
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
				flag = packService.updatePack(pack);
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

	// TODO: 取出快递集合，还得分类成已取出和未取出

	// 用户获取自己的快递
	@CrossOrigin
	@RequestMapping(value = "/getPacksByUser")
	public List<Pack> getPacksByUser(@RequestParam(value = "card") String card) {
		User user = userService.getUserById(card);
		String phone = user.getPhone();
		List<Pack> packs = packService.getPackSByPhone(phone);
		return packs;
	}

	// 管理员获取当前驿站所有的快递
	@CrossOrigin
	@RequestMapping(value = "/getPacksByAdmin")
	public List<Pack> getPacksByAdmin(@RequestParam(value = "card") String card) {
		Admin admin = adminService.getAdminById(card);
		String addr = admin.getAddr();
		List<Pack> packs = packService.getPacksByAddr(addr);
		return packs;
	}
}
