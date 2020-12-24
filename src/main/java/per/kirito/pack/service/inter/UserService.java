package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.User;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 21:02
 * @description:
 */
public interface UserService {
	int findUserByCardAndPwd(User user);

	int findUserByCardAndPhone(User user);

	int addUser(User user);

	User getUser(String card);

	int updateUser(User user);
}
