package indi.yuluo.springframework.test.common;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.PropertyValues;
import indi.yuluo.springframework.beans.factory.ConfigurableListableBeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:55
 */

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
		PropertyValues propertyValues = beanDefinition.getPropertyValues();

		propertyValues.addPropertyValue(new PropertyValue("company", "yuluo`s company"));
	}

}
