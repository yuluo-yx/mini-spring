package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Constructor;

import cn.hutool.core.bean.BeanUtil;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.PropertyValues;
import indi.yuluo.springframework.beans.factory.config.AutowireCapableBeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanPostProcessor;
import indi.yuluo.springframework.beans.factory.config.BeanReference;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:15
 * 实现bean的实例化操作
 */

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
		implements AutowireCapableBeanFactory {

	// 创建bean策略 调用
	private InstantiationStrategy instantiationStrategy = new CglibSubClassingInstantiationStrategy();

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {

		Object bean = null;
		try {
			bean = createBeanInstance(beanDefinition, beanName, args);
			// 给bean填充属性
			applyPropertyValues(beanName, bean, beanDefinition);
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

	/**
	 * Bean属性填充
	 * @param beanName			bean名
	 * @param bean				bean
	 * @param beanDefinition	bean定义
	 */
	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
		try {
			PropertyValues propertyValues = beanDefinition.getPropertyValues();
			for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
				String name = propertyValue.getName();
				Object value = propertyValue.getValue();

				if (value instanceof BeanReference beanReference) {
					// A 依赖 B， 获取B的实例化
					value = getBean(beanReference.getBeanName());
				}
				// 属性填充
				BeanUtil.setFieldValue(bean, name, value);
			}
		} catch (Exception e) {
			throw new BeansException("Error setting property values: " + beanName);

		}
	}

	public InstantiationStrategy getInstantiationStrategy() {
		return instantiationStrategy;
	}

	public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
		this.instantiationStrategy = instantiationStrategy;
	}

	private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
		// 1. 执行 BeanPostProcessor Before 处理
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

		// 待完成内容：invokeInitMethods(beanName, wrappedBean, beanDefinition);
		invokeInitMethods(beanName, wrappedBean, beanDefinition);

		// 2. 执行 BeanPostProcessor After 处理
		wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		return wrappedBean;
	}

	private void invokeInitMethods(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {

	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessBeforeInitialization(result, beanName);
			if (null == current) return result;
			result = current;
		}
		return result;
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
		Object result = existingBean;
		for (BeanPostProcessor processor : getBeanPostProcessors()) {
			Object current = processor.postProcessAfterInitialization(result, beanName);
			if (null == current) return result;
			result = current;
		}
		return result;
	}

}
