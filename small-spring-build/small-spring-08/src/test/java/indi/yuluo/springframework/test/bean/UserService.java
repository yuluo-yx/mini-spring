package indi.yuluo.springframework.test.bean;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.*;
import indi.yuluo.springframework.context.ApplicationContext;
import indi.yuluo.springframework.context.ApplicationContextAware;

public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

	private ApplicationContext applicationContext;
	private BeanFactory beanFactory;

	private String uId;
	private String company;
	private String location;
	private UserDao userDao;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("Bean Name is：" + name);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("ClassLoader：" + classLoader);
	}

	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

}
