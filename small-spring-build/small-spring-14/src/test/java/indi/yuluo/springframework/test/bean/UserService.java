package indi.yuluo.springframework.test.bean;

import java.util.Random;

import indi.yuluo.springframework.beans.factory.annotation.Autowired;
import indi.yuluo.springframework.beans.factory.annotation.Value;
import indi.yuluo.springframework.stereotype.Component;

@Component("userService")
public class UserService implements IUserService {

	@Value("${token}")
	private String token;

	@Autowired
	private UserDao userDao;

	public String queryUserInfo() {
		try {
			Thread.sleep(new Random(1).nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return userDao.queryUserName("10001") + "，" + token;
	}

	@Override
	public String register(String userName) {
		try {
			Thread.sleep(new Random(1).nextInt(100));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "注册用户：" + userName + " success！";
	}

	@Override
	public String toString() {
		return "UserService#token = { " + token + " }";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
