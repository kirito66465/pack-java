package per.kirito.pack.util;

import per.kirito.pack.myEnum.Express;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.pojo.Pack;
import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * author: kirito
 * date: 2020/12/23
 * time: 15:48
 * 包裹信息完善:自动生成取件码、更新取件状态、添加地址信息
 */
public class PackUtil {

	private static final int PACK_CODE_1 = Status.PACK_STATUS_1.getCode();
	private static final int PACK_CODE_0 = Status.PACK_STATUS_0.getCode();
	private static final int PACK_CODE__1 = Status.PACK_STATUS__1.getCode();

	// 快递公司的中文名与英文名
	private static final String ZTO = String.valueOf(Express.中通);
	private static final String STO = String.valueOf(Express.申通);
	private static final String YTO = String.valueOf(Express.圆通);
	private static final String JD = String.valueOf(Express.京东);
	private static final String SF = String.valueOf(Express.顺丰);
	private static final String YD = String.valueOf(Express.韵达);
	private static final String TT = String.valueOf(Express.天天);
	private static final String EMS = String.valueOf(Express.EMS);

	// 所在驿站
	private static final String ADDR_ZY = "中苑";
	private static final String ADDR_XY = "西苑";
	private static final String ADDR_BY = "北苑";

	// 驿站联系人
	private static final String CONT_ZY = "中苑快递员";
	private static final String CONT_XY = "西苑快递员";
	private static final String CONT_BY = "北苑快递员";

	// 驿站联系方式
	private static final String TEL_ZY = "13300000001";
	private static final String TEL_XY = "13300000002";
	private static final String TEL_BY = "13300000003";

	/**
	 * 添加入站信息(入站时只有快递单号这一条信息)
	 *
	 * @param pack 入站快递实体
	 * @return per.kirito.pack.pojo.Pack
	 */
	public static Pack addInfo(Pack pack) {
		String idAll = pack.getId();
		String id = idAll.substring(0, 2);
		String org = "";
		String addr = "";
		String contPer = "";
		String contTel = "";
		// 根据快递单号前2位获取快递公司和驿站相关信息
		switch (id) {
			case "75":
				org = ZTO;
				addr = ADDR_ZY;
				contPer = CONT_ZY;
				contTel = TEL_ZY;
				break;
			case "77":
				org = STO;
				addr = ADDR_ZY;
				contPer = CONT_ZY;
				contTel = TEL_ZY;
				break;
			case "YT":
				org = YTO;
				addr = ADDR_ZY;
				contPer = CONT_ZY;
				contTel = TEL_ZY;
				break;
			case "JD":
				org = JD;
				addr = ADDR_XY;
				contPer = CONT_XY;
				contTel = TEL_XY;
				break;
			case "SF":
				org = SF;
				addr = ADDR_XY;
				contPer = CONT_XY;
				contTel = TEL_XY;
				break;
			case "43":
				org = YD;
				addr = ADDR_XY;
				contPer = CONT_XY;
				contTel = TEL_XY;
				break;
			case "88":
				org = TT;
				addr = ADDR_BY;
				contPer = CONT_BY;
				contTel = TEL_BY;
				break;
			case "EA":
				org = EMS;
				addr = ADDR_BY;
				contPer = CONT_BY;
				contTel = TEL_BY;
				break;
			default:
				break;
		}
		// 添加快递公司信息
		pack.setOrg(org);
		// 添加所在驿站信息
		pack.setAddr(addr);
		// 添加驿站联系人信息
		pack.setCont_name(contPer);
		// 添加驿站联系方式信息
		pack.setCont_tel(contTel);
		// 添加快递状态信息
		Pack newPack = updateStatus(pack, "入站");
		String time = TypeConversion.getTime();
		// 添加快递入站时间
		newPack.setStart(time);
		return newPack;
	}

	/**
	 * 仅更新快递状态
	 *
	 * @param pack    快递实体
	 * @param operate 操作码
	 * @return per.kirito.pack.pojo.Pack
	 */
	public static Pack updateStatus(Pack pack, String operate) {
		if ("取件".equals(operate)) {
			pack.setStatus(PACK_CODE_0);
		} else if ("入站".equals(operate)) {
			pack.setStatus(PACK_CODE__1);
		}
		return pack;
	}

	/**
	 * 抽取出来的获取结果集方法，主要对status进行类型转换：jdbcType/Integer -> java/String
	 *
	 * @param packList Pack 结果集
	 * @return java.util.List<per.kirito.pack.pojo.utilPojo.PackResult>
	 */
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
	 * 抽取出来的分页方法，仅需传入当前页码、每页条数、快递结果集
	 *
	 * @param currentPage    当前页码
	 * @param pageSize       每页条数
	 * @param packResultList 快递结果集
	 * @return per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 */
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

}
