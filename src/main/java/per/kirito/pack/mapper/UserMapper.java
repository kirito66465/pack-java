package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import per.kirito.pack.pojo.User;

import java.util.Map;

/**
 * author: kirito
 * date: 2020/12/4
 * time: 21:00
 * User 的 Mapper 层接口
 */
public interface UserMapper {

	/**
	 * 根据学号和密码查询用户是否存在以进行登录判断
	 *
	 * @param card     学号
	 * @param password 密码
	 * @return int
	 */
	int login(@Param("card") String card, @Param("password") String password);

	/**
	 * 根据学号和手机号判断用户是否存在
	 *
	 * @param card  学号
	 * @param phone 手机号
	 * @return int
	 */
	int isExistByCardAndPhone(@Param("card") String card, @Param("phone") String phone);

	/**
	 * 根据 card 查询出该 User 信息
	 *
	 * @param card 学号
	 * @return per.kirito.pack.pojo.User
	 */
	User getUserById(String card);

	/**
	 * 根据 phone 查询出该 User 信息
	 *
	 * @param phone 手机号
	 * @return per.kirito.pack.pojo.User
	 */
	User getUserByPhone(String phone);

	/**
	 * User 注册
	 *
	 * @param user User 实体
	 * @return int
	 */
	int register(User user);

	/**
	 * User 忘记密码以进行密码重置
	 *
	 * @param card     学号
	 * @param password 密码
	 */
	void forgetPwd(@Param("card") String card, @Param("password") String password);

	/**
	 * User 修改密码
	 *
	 * @param card   学号
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @return int
	 */
	int resetPwd(@Param("card") String card, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	/**
	 * User 更新信息
	 *
	 * @param card 学号
	 * @param name 姓名
	 * @param addr 地址
	 * @param mail 邮箱
	 * @return int
	 */
	int updateInfo(@Param("card") String card, @Param("name") String name, @Param("addr") String addr, @Param("mail") String mail);

	/**
	 * 随机获取一个 User 信息
	 *
	 * @return per.kirito.pack.pojo.User
	 */
	User getUserByRand();

	/**
	 * 更新 count 数-1
	 *
	 * @param id 快递单号
	 * @return int
	 */
	int updateCountLessByPackId(String id);

	/**
	 * 更新 count 数+1
	 *
	 * @param id 快递单号
	 * @return int
	 */
	int updateCountPlusByPackId(String id);

	/**
	 * 根据快递单号查询出收件人邮箱、取件码、驿站地址、快递公司信息
	 *
	 * @param id 快递单号
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> getMailById(String id);

}
