package indi.yuluo.springframework.test;

import indi.yuluo.springframework.beans.factory.PropertyValue;
import indi.yuluo.springframework.beans.factory.PropertyValues;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanReference;
import indi.yuluo.springframework.beans.factory.support.DefaultListableBeanFactory;
import indi.yuluo.springframework.test.bean.UserDao;
import indi.yuluo.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class ApiTest {

	@Test
	public void testBeanFactoryProperty() {

		// 初始化BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 注册bean
		beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

		// UserService 设置属性
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
		propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

		// UserService 注入 bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 获取bean
		UserService userService = (UserService) beanFactory.getBean("userService");
		userService.queryUserInfo();

	}

}
