package per.kirito.pack.mapper;

import per.kirito.pack.pojo.Code;

/**
 * author: kirito
 * date: 2020/12/23
 * time: 17:21
 * Code 的 Mapper 层接口
 */
public interface CodeMapper {

	/**
	 * 查询所在驿站最大取件码状态是否为未使用，且从未被使用过
	 * @param code  取件码实体
	 * @return int
	 */
	int findMaxCode(Code code);

	/**
	 * 更新 Code
	 * @param code  取件码实体
	 * @return int
	 */
	int updateCode(Code code);

	/**
	 * 当最大取件码被使用过时，根据驿站地址查询出当前最早被释放的取件码
	 * @param addr  驿站地址
	 * @return per.kirito.pack.pojo.Code
	 */
	Code findCodeFreeMin(String addr);

}
