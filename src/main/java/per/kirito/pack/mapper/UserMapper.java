package per.kirito.pack.mapper;

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

	int findUserByCardAndPwd(User user);

	int findUserByCardAndPhone(User user);

	User getUserById(String card);

	User getUserByPhone(String phone);

	int addUser(User user);

	int updateUser(User user);

	User getUserByRand();

}
