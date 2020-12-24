package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.AdminMapper;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AdminService;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/11 20:09
 * @description:
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public int findAdminByCardAndPwd(Admin admin) {
		return adminMapper.findAdminByCardAndPwd(admin);
	}

	@Override
	public Admin getAdmin(String card) {
		return adminMapper.getAdmin(card);
	}

	@Override
	public int updateAdmin(Admin admin) {
		return adminMapper.updateAdmin(admin);
	}
}
