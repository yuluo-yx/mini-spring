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
			Object bean = getSingleton(name);
			if (bean != null) {
				return bean;
			}

			BeanDefinition beanDefinition = getBeanDefinition(name);
			return createBean(name, beanDefinition);
	}

	protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

	protected abstract Object createBean(String name, BeanDefinition beanDefinition) throws BeansException;

}
