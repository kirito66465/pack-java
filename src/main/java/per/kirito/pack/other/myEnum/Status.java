package per.kirito.pack.other.myEnum;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/24
 * @Time: 10:30
 * @description: 状态码与值枚举类
 */
public enum Status {

	// 登录
	LOGIN_SUCCESS(1, "login success", "登录成功!"),
	LOGIN_FAIL(0, "login fail", "登录失败!"),
	// 注册
	REGISTER_SUCCESS(1, "register success", "注册成功!"),
	REGISTER_FAIL(0, "register fail", "注册失败!"),
	// 退出
	EXIT_SUCCESS(1, "exit success", "退出成功!"),
	EXIT_FAIL(0, "exit fail", "退出失败!"),
	// 修改密码
	PWD_SUCCESS(1, "update password success", "修改密码成功！"),
	PWD_FAIL(0, "update password fail", "修改密码失败！"),
	// 取件
	PICK_SUCCESS(1, "pick up the package success", "取件成功！"),
	PICK_FAIL(0, "pick up the package fail", "取件失败！"),
	// 入站
	INTO_SUCCESS(1, "the package enter addr success", "快递入站成功！"),
	INTO_FAIL(0, "the package enter addr fail", "快递入站失败！"),
	// 获取信息
	INFO_SUCCESS(1, "get info success", "获取信息成功！"),
	INFO_FAIL(0, "get info fail", "获取信息失败！"),
	// 是否存在
	IS_EXIST(1, "is exist", "存在！"),
	NOT_EXIST(0, "not exist", "不存在！"),
	// 操作失败
	DO_SUCCESS(1, "do success", "操作成功！"),
	DO_FAIL(0, "do fail", "操作失败！"),
	// 是否使用
	IS_USE(1, "has been used", "已被使用"),
	NOT_USE(0, "has not been used", "已被使用"),
	// 快递状态
	PACK_STATUS_1(1, "未取出", "未取出"),
	PACK_STATUS_0(0, "已取出", "已取出"),
	PACK_STATUS__1(-1, "未有取件码", "未有取件码"),
	SEND_STATUS_0(0, "已提交", "已提交"),
	SEND_STATUS_1(1, "已支付", "已支付"),
	SEND_STATUS_2(2, "已确认", "已确认"),
	SEND_STATUS_3(3, "已发出", "已发出"),
	CODE_STATUS_1(1, "已被使用过", "已被使用过"),
	CODE_STATUS_0(0, "未被使用过", "未被使用过"),
	TAKE_SUCCESS(1, "take over", "代取成功"),
	LOGIN_TO_DO(-1, "please login to operate", "请登录再操作"),
	CODE_ERR(-1, "check code is wrong", "验证码输入错误"),
	PWD_ERR(-1, "password not same", "原密码输入错误");

	private final int code;
	private final String enMsg;
	private final String zhMsg;

	private Status(int code, String enMsg, String zhMsg) {
		this.code = code;
		this.enMsg = enMsg;
		this.zhMsg = zhMsg;
	}

	public int getCode() {
		return this.code;
	}

	public String getEnMsg() {
		return this.enMsg;
	}

	public String getZhMsg() {
		return this.zhMsg;
	}

}