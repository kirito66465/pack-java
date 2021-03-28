package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.utilPojo.EchartsVO;

import java.util.Map;

/**
 * author: kirito
 * date: 2021/3/28
 * time: 17:29
 * Mail 的 Service 层接口
 */
public interface EchartsService {

	/**
	 * 获取数据
	 * @param datee     日期
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	Map<String, Object> getData(String datee, String token);

}
