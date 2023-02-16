package indi.yuluo.springframework.test.bean;

public class UserService {

	private String uId;

	private String company;

	private String location;

	private IUserDao userDao;

	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}

	String getuId() {
		return uId;
	}

	void setuId(String uId) {
		this.uId = uId;
	}

	String getCompany() {
		return company;
	}

	void setCompany(String company) {
		this.company = company;
	}

	String getLocation() {
		return location;
	}

	void setLocation(String location) {
		this.location = location;
	}

	IUserDao getUserDao() {
		return userDao;
	}

	void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
}
