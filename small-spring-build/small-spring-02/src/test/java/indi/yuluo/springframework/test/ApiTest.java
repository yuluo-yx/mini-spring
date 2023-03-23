package indi.yuluo.springframework.test;

import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.support.DefaultListableBeanFactory;
import indi.yuluo.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class ApiTest {

	@Test
	public void test_BeanFactory(){
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2.注册 bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 3.第一次获取 bean
		UserService userService = (UserService) beanFactory.getBean("userService");
		userService.queryUserInfo();

		// 4.第二次获取 bean from Singleton
		UserService userService_singleton = (UserService) beanFactory.getSingleton("userService");
		userService_singleton.queryUserInfo();
	}

}
