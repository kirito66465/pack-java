package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Send;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/5
 * @Time: 11:14
 * @description: Send的Mapper层接口
 */
@Repository
public interface SendMapper {

	/**
	 * @Description: 添加寄件
	 * @Param: [send]
	 * @Return: int
	 **/
	int addSend(Send send);

	/**
	 * @Description: 删除寄件
	 * @Param: [id]
	 * @Return: int
	 **/
	int deleteSend(String id);

	/**
	 * @Description: 更新寄件状态
	 * @Param: [id, status, dt]
	 * @Return: int
	 **/
	int updateSend(@Param("id") String id, @Param("status") String status, @Param("dt") String dt);

	/**
	 * @Description: 获取User的寄件集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Send>
	 **/
	List<Send> getSendByUser(String card);

	/**
	 * @Description: 获取Admin的寄件集合
	 * @Param: [org]
	 * @Return: java.util.List<per.kirito.pack.pojo.Send>
	 **/
	List<Send> getSendByAdmin(String org);

	/**
	 * @Description: 传入状态，获取User的该状态寄件数量
	 * @Param: [card, status]
	 * @Return: int
	 **/
	int getUserTotal(@Param("card") String card, @Param("status") String status);

	/**
	 * @Description: 传入状态，获取Admin的该状态寄件数量
	 * @Param: [org, status]
	 * @Return: int
	 **/
	int getAdminTotal(@Param("org") String org, @Param("status") String status);

}
