package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Admin;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/11 20:08
 * @description:
 */
public interface AdminService {
	int findAdminByCardAndPwd(Admin admin);

	Admin getAdminById(String card);

	Admin getAdminByAddr(String addr);

	int updateAdmin(Admin admin);
}
