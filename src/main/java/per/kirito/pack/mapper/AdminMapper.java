package per.kirito.pack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import per.kirito.pack.pojo.Admin;

/**
 * @author kirito
 * @date 2020/12/11
 * @time 19:54
 * Admin 的 Mapper 层接口
 */
public interface AdminMapper extends BaseMapper<Admin> {

	/**
	 * 更新 count 数-1
	 *
	 * @param id 快递单号
	 */
	void updateCountLessByPackId(String id);

	/**
	 * 更新 count 数+1
	 *
	 * @param id 快递单号
	 */
	void updateCountPlusByPackId(String id);

}
