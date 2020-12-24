package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.pojo.User;
import per.kirito.pack.service.inter.UserService;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 21:03
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public int findUserByCardAndPwd(User user) {
		return userMapper.findUserByCardAndPwd(user);
	}

	@Override
	public int findUserByCardAndPhone(User user) {
		return userMapper.findUserByCardAndPhone(user);
	}

	@Override
	public int addUser(User user) {
		return userMapper.addUser(user);
	}

	@Override
	public User getUser(String card) {
		return userMapper.getUser(card);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateUser(user);
	}
}
