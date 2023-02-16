package indi.yuluo.springframework.test.bean;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class UserService {

	// 加入参数，验证含有参数的bean能否被实例化
	private String name;

	public UserService(String name) {
		this.name = name;
	}

	public void queryUsserInfo() {
		System.out.println("查询用户信息！" + name);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("");
		sb.append("").append(name);

		return sb.toString();
	}
}
