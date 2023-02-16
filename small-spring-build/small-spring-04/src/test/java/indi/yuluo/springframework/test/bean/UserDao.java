package indi.yuluo.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 *@author yuluo
 *@createTime 2023-01-26  20:13
 */

public class UserDao {

	private static Map<String, String> map = new HashMap<>();

	static {
		map.put("10001", "yuluo");
		map.put("10002", "mumu");
		map.put("10003", "test");
	}

	public String queryUserName(String uId) {
		return map.get(uId);
	}

}
