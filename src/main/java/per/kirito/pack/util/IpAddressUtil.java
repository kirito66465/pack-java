package per.kirito.pack.util;

import javax.servlet.http.HttpServletRequest;

/**
 * author: kirito
 * date: 2021/3/9
 * time: 22:37
 * description: IP 地址 工具类
 */
public class IpAddressUtil {

	/**
	 * 获取发送请求的客户端地址
	 * @param request   http 请求对象
	 * @return java.lang.String
	 **/
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && !ip.equals("") && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个 ip 值，第一个 ip 才是真实 ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (ip != null && !"".equals(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

}
