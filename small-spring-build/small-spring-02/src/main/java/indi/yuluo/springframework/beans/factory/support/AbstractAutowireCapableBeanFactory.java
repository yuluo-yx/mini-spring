package indi.yuluo.springframework.beans.factory.support;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:15
 * 实现bean的实例化操作
 */

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {

		Object bean = null;
		try {
			bean = beanDefinition.getBeanClass().newInstance();
		}
		catch (InstantiationException | IllegalAccessException e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		addSingleton(beanName, bean);

		return bean;
	}
}
