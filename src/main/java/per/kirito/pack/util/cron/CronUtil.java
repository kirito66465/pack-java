package per.kirito.pack.util.cron;

import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.system.SystemUtil;

/**
 * author: kirito
 * date: 2021/3/3
 * time: 21:36
 * description: 定时任务
 */
public class CronUtil {

	private static Log log = LogFactory.get();

	/**
	 * 定时输出细腻系
	 **/
	public static void printInfo() {
		log.info("==============================================================");
		log.info(DateUtil.now());
		log.info(String.valueOf(SystemUtil.getOsInfo()));
		log.info(String.valueOf(SystemUtil.getRuntimeInfo()));
		log.info("==============================================================");
	}

}
