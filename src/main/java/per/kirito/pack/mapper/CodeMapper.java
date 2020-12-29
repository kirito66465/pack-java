package per.kirito.pack.mapper;

import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Code;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 17:21
 * @description:
 */
@Repository
public interface CodeMapper {

	/**
	 * @Description: 查找该Code
	 * @Param: [code]
	 * @Return: int
	 **/
	int findCode(Code code);

	/**
	 * @Description: 更新Code
	 * @Param: [code]
	 * @Return: int
	 **/
	int updateCode(Code code);

	List<Code> getAllCodes();

}
