package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Constructor;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:15
 * 实现bean的实例化操作
 */

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	// 创建bean策略 调用
	private InstantiationStrategy instantiationStrategy = new CglibSubClassingInstantiationStrategy();

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {

		Object bean = null;
		try {
			bean = createBeanInstance(beanDefinition, beanName, args);
		}
		catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		// 添加Bean实例
		addSingleton(beanName, bean);

		return bean;
	}

	protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {

		Constructor constructorToUser = null;
		Class<?> beanClass = beanDefinition.getBeanClass();

		Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

		for (Constructor<?> ctor : declaredConstructors) {
			if (args != null && ctor.getParameterTypes().length == args.length) {
				constructorToUser = ctor;
				break;
			}
		}

		return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUser, args);

	}
}
