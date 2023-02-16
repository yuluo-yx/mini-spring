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
	public void testBeanFactoryWithCglib() {
		// 初始化BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 注册bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 获取bean
		UserService userService = (UserService) beanFactory.getBean("userService", "yuluo");
		System.out.println(userService.toString());
		userService.queryUsserInfo();

	}

}
