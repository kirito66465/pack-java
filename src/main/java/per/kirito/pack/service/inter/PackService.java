package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Pack;

import java.util.List;
import java.util.Map;

/**
 * @author kirito
 * @date 2020/12/23
 * @time 15:35
 * Pack 的 Service 层接口
 */
public interface PackService {

	/**
	 * 驿站管理员添加快递入站
	 *
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	String addPack(String id, String token);

	/**
	 * User 进行取件请求，仅传入快递单号和 token
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 */
	String pickById(String ids, String token);

	/**
	 * User 进行取件，必须传入驿站地址和取件码
	 *
	 * @param addr  驿站地址
	 * @param code  取件码
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	String pickPackByUser(String addr, String code, String token);

	/**
	 * Admin 进行取件，仅传入快递单号即可
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	String pickPackByAdmin(String ids, String token);

	/**
	 * 根据快递单号删除此快递
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	String deletePacksById(String ids, String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * 分页获取 User 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态, search:搜索}
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getUserPackByPage(String json);

	/**
	 * 分页获取 User 已取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, search:搜索}
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getUserIsPick(String json);

	/**
	 * 分页获取 User 所未取出的快递， 无论有无取件码；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态, search:搜索}
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getUserNoPick(String json);

	/**
	 * 获取 User 所有快递总数、已取快递数量、未取快递数量
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getUserTotalNum(String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * 分页获取 Admin 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态, search:搜索}
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getAdminPackByPage(String json);

	/**
	 * 分页获取当前驿站的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, search:搜索}
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getAdminIsPick(String json);

	/**
	 * 分页获取当前驿站的未取出快递，无论有无取件码；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json 参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态, search:搜索}
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getAdminNoPick(String json);

	/**
	 * 获取 Admin 所有快递总数、已取快递数量、未取快递数量
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getAdminTotalNum(String token);

	/**
	 * 根据驿站地址和货架获取当前货架的所有快递
	 *
	 * @param token 令牌
	 * @param shelf 货架
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 **/
	Map<String, Object> getShelfPack(String token, String shelf);

	/**
	 * 获取不筛选不分页的驿站所有快递集合
	 *
	 * @param token 令牌
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAllPacksByExcelOfAdmin(String token);

	/**
	 * 获取不筛选不分页的驿站已取快递集合
	 *
	 * @param token 令牌
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getIsPacksByExcelOfAdmin(String token);

	/**
	 * 获取不筛选不分页的驿站未取快递集合
	 *
	 * @param token 令牌
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getNoPacksByExcelOfAdmin(String token);

}
