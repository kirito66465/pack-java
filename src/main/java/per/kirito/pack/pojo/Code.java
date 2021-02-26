package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * author: 严晨
 * date: 2020/12/23
 * time: 17:20
 * 取件码存放表
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_code")
public class Code {

	/** 取件码 */
	private String code;
	/** 所在驿站 */
	private String addr;
	/** 状态，0：未被使用，1：已被使用 */
	private Integer status;
	/** 释放时间 */
	private String free;

}
