package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 17:20
 * @description: 取件码存放表
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_code")
public class Code {

	private String code;        // 取件码
	private String addr;        // 所在驿站
	private int status;         // 状态，0：未被使用，1：已被使用

}
