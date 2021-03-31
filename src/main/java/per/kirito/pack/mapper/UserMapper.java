package per.kirito.pack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.kirito.pack.pojo.User;

import java.util.Map;

/**
 * @author kirito
 * @date 2020/12/4
 * @time 21:00
 * User 的 Mapper 层接口
 */
public interface UserMapper extends BaseMapper<User> {

	/**
	 * 随机获取一个 User 信息
	 *
	 * @return per.kirito.pack.pojo.User
	 */
	User getUserByRand();

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

	/**
	 * 根据快递单号查询出收件人邮箱、取件码、驿站地址、快递公司信息
	 *
	 * @param id 快递单号
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> getMailById(String id);

}
