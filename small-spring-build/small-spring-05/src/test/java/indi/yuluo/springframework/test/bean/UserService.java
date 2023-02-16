package indi.yuluo.springframework.test.bean;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class UserService {

	private String uId;

	// service注入dao 体现bean的依赖关系
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// 添加构造参数解决 Superclass has no null constructors but no arguments were given 问题
	public String getuId() {
		return uId;
	}


	public String queryUserInfo() {
		return userDao.queryUserName(uId);
	}
}
