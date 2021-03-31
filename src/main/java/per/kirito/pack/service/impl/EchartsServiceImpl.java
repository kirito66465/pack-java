package per.kirito.pack.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.EchartsMapper;
import per.kirito.pack.myenum.Status;
import per.kirito.pack.pojo.Echarts;
import per.kirito.pack.pojo.utilpojo.EchartsVO;
import per.kirito.pack.service.inter.EchartsService;
import per.kirito.pack.util.TypeConversion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:36
 * Echarts 的 Service 层
 */
@Slf4j
@Service
public class EchartsServiceImpl implements EchartsService {

	@Autowired
	private EchartsMapper echartsMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final String INFO_FAIL = Status.INFO_FAIL.getEnMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getEnMsg();

	/**
	 * 获取数据
	 *
	 * @param datee 日期
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@Override
	public Map<String, Object> getData(String datee, String token) {
		Map<String, Object> map = new HashMap<>();
		if (stringRedisTemplate.hasKey(token)) {
			// 取出 Admin 登录的 card
			String card = stringRedisTemplate.opsForValue().get(token);
			String date = "";
			if (datee == null || "".equals(datee)) {
				date = DateUtil.today();
			} else {
				date = datee;
			}
			QueryWrapper<Echarts> echartsQueryWrapper = new QueryWrapper<>();
			echartsQueryWrapper.eq("datee", date).eq("card", card);
			Echarts echarts = echartsMapper.selectOne(echartsQueryWrapper);
			if (echarts == null) {
				map.put("fail", NOT_EXIST);
				log.info("date: {}, card: {}, 此日暂无人员取件！", date, card);
			} else {
				EchartsVO echartsVO = TypeConversion.getData(echarts);
				map.put("result", echartsVO);
				log.info("date: {}, card: {}, echarts-data: {}", date, card, echartsVO.toString());
			}
		} else {
			log.info("token: {} 获取 Echarts 数据失败，因为登录状态失效！", token);
			map.put("fail", INFO_FAIL);
		}
		return map;
	}

}
