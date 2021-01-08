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

	int findAdminByCardAndPwd(Admin admin);

	Admin getAdminById(String card);

	Admin getAdminByAddr(String addr);

	int updateAdmin(Admin admin);

	int resetPwd(@Param("card") String card, @Param("oldPwd") String oldPwd, @Param("newPwd") String newPwd);

	int updateInfo(@Param("card") String card, @Param("name") String name, @Param("phone") String phone);

}
