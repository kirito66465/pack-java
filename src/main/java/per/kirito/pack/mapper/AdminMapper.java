package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Admin;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/11 19:54
 * @description: Admin的Mapper层接口
 */
@Repository
public interface AdminMapper {

	/**
	 * @Description: 根据card和密码查询用户是否存在以进行登录判断
	 * @Param: [card, password]
	 * @Return: int
	 **/
	int login(@Param("card") String card, @Param("password") String password);

	/**
	 * @Description: 根据card查询出该Admin信息
	 * @Param: [card]
	 * @Return: per.kirito.pack.pojo.Admin
	 **/
	Admin getAdminById(String card);

	/**
	 * @Description: 根据驿站地址查询出该Admin信息
	 * @Param: [addr]
	 * @Return: per.kirito.pack.pojo.Admin
	 **/
	Admin getAdminByAddr(String addr);

	/**
	 * @Description: 修改密码
	 * @Param: [card, oldPwd, newPwd]
	 * @Return: int
	 **/
	int resetPwd(@Param("card") String card, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	/**
	 * @Description: 更新Admin信息
	 * @Param: [card, name, phone]
	 * @Return: int
	 **/
	int updateInfo(@Param("card") String card, @Param("name") String name, @Param("phone") String phone);

	/**
	 * @Description: 更新count数-1
	 * @Param: [id]
	 * @Return: int
	 **/
	int updateCountLessByPackId(String id);

	/**
	 * @Description: 更新count数+1
	 * @Param: [id]
	 * @Return: int
	 **/
	int updateCountPlusByPackId(String id);

}
