package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/6 16:04
 * @description: 消息实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_msg")
public class Message {

	/** 序号 */
	private int id;
	/** 发信人 */
	private String sender;
	/** 收信人 */
	private String receiver;
	/** 消息内容 */
	private String from_msg;
	/** 回复内容 */
	private String to_reply;
	/** 发信时间 */
	private String send;
	/** 回复时间 */
	private String reply;

}
