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
	public void testBeanFactory() {
		// 初始化BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 注册bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 第一次获取bean
		UserService userService = (UserService) beanFactory.getBean("userService");
		System.out.println(userService.toString());
		userService.queryUsserInfo();

		// 第二次获取bean from Singleton
		UserService serviceSingleton = (UserService) beanFactory.getBean("userService");
		System.out.println(serviceSingleton.toString());
		serviceSingleton.queryUsserInfo();
	}

}
