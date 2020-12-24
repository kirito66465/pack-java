package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Code;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 17:23
 * @description:
 */
public interface CodeService {
	int findCode(Code code);

	int updateCode(Code code);
}
