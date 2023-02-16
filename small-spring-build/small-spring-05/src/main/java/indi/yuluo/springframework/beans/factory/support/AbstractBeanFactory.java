package indi.yuluo.springframework.beans.factory.support;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.BeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:15
 * 抽象类定义模板方法
 */

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return doGetBean(name, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return (T) getBean(name);
	}

	protected <T> T doGetBean(final String name, final Object[] args) {
		Object bean = getSingleton(name);

		if (bean != null) {
			return (T) bean;
		}

		BeanDefinition beanDefinition = getBeanDefinition(name);

		return (T) createBean(name, beanDefinition, args);
	}

	protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

	protected abstract Object createBean(String name, BeanDefinition beanDefinition, Object[] args) throws BeansException;

}
