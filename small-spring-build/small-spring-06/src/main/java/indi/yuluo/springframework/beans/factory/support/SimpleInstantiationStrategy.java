package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 *@author yuluo
 *@createTime 2023-01-26  18:08
 * JDK实例化
 */

public class SimpleInstantiationStrategy implements InstantiationStrategy {

	@Override
	public Object instantiate(BeanDefinition beanDefinition, String beanName,
			Constructor ctor, Object[] args) throws BeansException {

		Class beanClass = beanDefinition.getBeanClass();

		try {
			// 不为空为需要有构造参数
			if (ctor != null) {
				return beanClass.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
			} else {
				// 为空是无构造参数
				return beanClass.getDeclaredConstructor().newInstance();
			}
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
		}
	}

}
