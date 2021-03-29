package per.kirito.pack.util.cron;

import cn.hutool.core.date.DateUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kirito
 * @date 2021/3/3
 * @time 21:36
 * 定时任务
 */
@Slf4j
public class CronUtil {

	/**
	 * 定时输出细腻系
	 **/
	public static void printInfo() {
		log.info("==============================================================");
		log.info(DateUtil.now());
		log.info("\n{}", String.valueOf(SystemUtil.getOsInfo()));
		log.info("\n{}", String.valueOf(SystemUtil.getRuntimeInfo()));
		log.info("==============================================================");
	}

}
