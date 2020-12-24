package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.myEnum.Express;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.pojo.Code;
import per.kirito.pack.pojo.Pack;
import per.kirito.pack.service.inter.AdminService;
import per.kirito.pack.service.inter.CodeService;
import per.kirito.pack.service.inter.PackService;
import per.kirito.pack.util.PackUtil;

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

	@CrossOrigin
	@RequestMapping(value = "/addPack")
	public String addPack(@RequestParam(value = "id") String id) {           // TODO: 改成添加参数，其余自动生成
		// TODO: 管理员登录时，在Redis中存储所在驿站addr
		// 取出登录的管理员的编号
		String card = stringRedisTemplate.opsForValue().get("card");
		String addr = "";
		switch (card) {
			case "2101": addr = "中苑"; break;
			case "2102": addr = "西苑"; break;
			case "2103": addr = "北苑"; break;
			default: break;
		}
		Pack pack = new Pack();
		pack.setId(id);
		Admin admin = adminService.getAdmin(card);
		int count = admin.getCount();
		// 查询最大取件码有无被使用
		Code code = new Code("20-6-20", addr, NOT_USE);
		int isUse = codeService.findCode(code);
		// TODO: 添加取件码
		// TODO: 判断取件码有无被使用

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
}
