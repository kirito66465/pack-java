package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.User;

import java.util.Map;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 21:00
 * @description: User的Mapper层接口
 */
@Repository
public interface UserMapper {

	/**
	 * @Description: 根据学号和密码查询用户是否存在以进行登录判断
	 * @Param: [card, password]
	 * @Return: int
	 **/
	int login(@Param("card") String card, @Param("password") String password);

	/**
	 * @Description: 根据card查询出该User信息
	 * @Param: [card]
	 * @Return: per.kirito.pack.pojo.User
	 **/
	User getUserById(String card);

	/**
	 * @Description: 根据phone查询出该User信息
	 * @Param: [phone]
	 * @Return: per.kirito.pack.pojo.User
	 **/
	User getUserByPhone(String phone);

	/**
	 * @Description: User注册
	 * @Param: [user]
	 * @Return: int
	 **/
	int register(User user);

	/**
	 * @Description: User忘记密码以进行密码重置
	 * @Param: [card, password, phone]
	 * @Return: int
	 **/
	int forgetPwd(@Param("card") String card, @Param("password") String password, @Param("phone") String phone);

	/**
	 * @Description: User修改密码
	 * @Param: [card, oldPwd, newPwd]
	 * @Return: int
	 **/
	int resetPwd(@Param("card") String card, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	/**
	 * @Description: User更新信息
	 * @Param: [card, name, addr, mail]
	 * @Return: int
	 **/
	int updateInfo(@Param("card") String card, @Param("name") String name, @Param("addr") String addr, @Param("mail") String mail);

	/**
	 * @Description: 随机获取一个User信息
	 * @Param: []
	 * @Return: per.kirito.pack.pojo.User
	 **/
	User getUserByRand();

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

	/**
	 * @Description: 根据快递单号查询出收件人邮箱、取件码、驿站地址、快递公司信息
	 * @Param: [id]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> getMailById(String id);

}
