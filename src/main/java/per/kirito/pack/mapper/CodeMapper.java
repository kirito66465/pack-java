package per.kirito.pack.mapper;

import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Code;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 17:21
 * @description: Code的Mapper层接口
 */
@Repository
public interface CodeMapper {

	/**
	 * @Description: 查询所在驿站最大取件码状态是否为未使用，且从未被使用过
	 * @Param: [code]
	 * @Return: int
	 **/
	int findMaxCode(Code code);

	/**
	 * @Description: 更新Code
	 * @Param: [code]
	 * @Return: int
	 **/
	int updateCode(Code code);

	/**
	 * @Description: 根据驿站地址查询出当前
	 * @Param: [addr]
	 * @Return: per.kirito.pack.pojo.Code
	 **/
	Code findCodeFreeMin(String addr);

}
