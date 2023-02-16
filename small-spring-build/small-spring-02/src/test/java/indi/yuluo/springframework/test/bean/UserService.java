package indi.yuluo.springframework.test.bean;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class UserService {

	private String name;

	public UserService(String name) {
		this.name = name;
	}

	public void queryUsserInfo() {
		System.out.println("查询用户信息！");
	}

}
