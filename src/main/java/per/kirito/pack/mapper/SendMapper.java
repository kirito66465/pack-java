package per.kirito.pack.mapper;

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

	int addSend(Send send);

	int deleteSend(String id);

	int updateSend(Send send);

	List<Send> getSendByUser(String card);

	List<Send> getSendByAdmin(String org);

	int getTotalByUser(String card);

	int getTotalByAdmin(String org);

}
