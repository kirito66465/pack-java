package per.kirito.pack.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author: 严晨
 * date: 2021/2/26
 * time: 14:59
 * elasticsearch 参数类
 */
@Component
@ConfigurationProperties(prefix = "elastic")
public class ElasticProperties {

	/**
	 * 主机名
	 */
	private String hostName;

	/**
	 * 端口
	 */
	private Integer port;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}
