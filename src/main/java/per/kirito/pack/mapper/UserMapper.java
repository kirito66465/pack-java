package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.User;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 21:00
 * @description: User的Mapper层接口
 */
@Repository
public interface UserMapper {

	int findUserByCard(String card);

	int login(User user);

	int findUserByCardAndPhone(User user);

	User getUserById(String card);

	User getUserByPhone(String phone);

	int register(User user);

	int updateUser(User user);

	int forgetPwd(User user);

	int resetPwd(@Param("card") String card, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	int updateInfo(@Param("card") String card, @Param("name") String name, @Param("addr") String addr);

	User getUserByRand();

}
