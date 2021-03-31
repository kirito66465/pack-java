package per.kirito.pack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.kirito.pack.pojo.Code;

/**
 * @author kirito
 * @date 2020/12/23
 * @time 17:21
 * Code 的 Mapper 层接口
 */
public interface CodeMapper extends BaseMapper<Code> {

	/**
	 * 当最大取件码被使用过时，根据驿站地址查询出当前最早被释放的取件码
	 *
	 * @param addr 驿站地址
	 * @return per.kirito.pack.pojo.Code
	 */
	Code findCodeFreeMin(String addr);

}
