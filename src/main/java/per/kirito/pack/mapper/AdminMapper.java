package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import per.kirito.pack.pojo.Admin;

/**
 * author: 严晨
 * date: 2020/12/11
 * time: 19:54
 * Admin 的 Mapper 层接口
 */
public interface AdminMapper {

	/**
	 * 根据 card 和密码查询用户是否存在以进行登录判断
	 * @param card      编号
	 * @param password  密码
	 * @return int
	 */
	int login(@Param("card") String card, @Param("password") String password);

	/**
	 * 根据 card 查询出该 Admin 信息
	 * @param card  编号
	 * @return per.kirito.pack.pojo.Admin
	 */
	Admin getAdminById(String card);

	/**
	 * 根据驿站地址查询出该 Admin 信息
	 * @param addr  驿站地址
	 * @return per.kirito.pack.pojo.Admin
	 */
	Admin getAdminByAddr(String addr);

	/**
	 * 修改密码
	 * @param card      编号
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
	 * @return int
	 */
	int resetPwd(@Param("card") String card, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	/**
	 * 更新 Admin 信息
	 * @param card  编号
	 * @param name  姓名
	 * @param phone 手机号
	 * @return int
	 */
	int updateInfo(@Param("card") String card, @Param("name") String name, @Param("phone") String phone);

	/**
	 * 更新 count 数-1
	 * @param id    快递单号
	 * @return int
	 */
	int updateCountLessByPackId(String id);

	/**
	 * 更新 count 数+1
	 * @param id    快递单号
	 * @return int
	 */
	int updateCountPlusByPackId(String id);

}
