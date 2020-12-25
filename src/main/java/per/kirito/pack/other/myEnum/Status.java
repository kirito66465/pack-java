package per.kirito.pack.other.myEnum;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/24
 * @Time: 10:30
 * @description:
 */
public enum Status {

	LOGIN_SUCCESS(1, "login success", "登录成功!"),
	LOGIN_FAIL(0, "login fail", "登录失败!"),
	EXIT_SUCCESS(1, "exit success", "退出成功!"),
	EXIT_FAIL(0, "exit fail", "退出失败!"),
	REGISTER_SUCCESS(1, "register success", "注册成功!"),
	REGISTER_FAIL(0, "register fail", "注册失败!"),
	PWD_SUCCESS(1, "update password success", "修改密码成功！"),
	PWD_FAIL(0, "update password fail", "修改密码失败！"),
	PICK_SUCCESS(1, "pick up the package success", "取件成功！"),
	PICK_FAIL(0, "pick up the package fail", "取件失败！"),
	INTO_SUCCESS(1, "the package enter addr success", "快递入站成功！"),
	INTO_FAIL(0, "the package enter addr fail", "快递入站失败！"),
	INFO_SUCCESS(1, "get info success", "获取信息成功！"),
	INFO_FAIL(0, "get info fail", "获取信息失败！"),
	IS_EXIST(1, "is exist", "存在！"),
	NOT_EXIST(0, "not exist", "不存在！"),
	DO_SUCCESS(1, "do success", "操作成功！"),
	DO_FAIL(0, "do fail", "操作失败！"),
	IS_USE(1, "has been used", "已被使用"),
	NOT_USE(0, "has not been used", "已被使用");


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

	public String getMsg() {
		return this.enMsg;
		// if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
		// 	return this.zhMsg;
		// } else {
		// 	return this.enMsg;
		// }
	}
}