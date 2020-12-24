package per.kirito.pack.mapper;

import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Admin;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/11 19:54
 * @description:
 */
@Repository
public interface AdminMapper {
	int findAdminByCardAndPwd(Admin admin);

	Admin getAdmin(String card);

	int updateAdmin(Admin admin);
}
