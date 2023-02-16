package indi.yuluo.springframework.test.common;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanPostProcessor;
import indi.yuluo.springframework.test.bean.UserService;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:55
 */

public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if ("userService".equals(beanName)) {
			UserService userService = (UserService) bean;
			userService.setLocation("改为：test");
		}

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
