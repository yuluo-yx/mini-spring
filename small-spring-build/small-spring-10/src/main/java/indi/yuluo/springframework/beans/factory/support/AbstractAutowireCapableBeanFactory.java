package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.PropertyValues;
import indi.yuluo.springframework.beans.factory.Aware;
import indi.yuluo.springframework.beans.factory.BeanClassLoaderAware;
import indi.yuluo.springframework.beans.factory.BeanFactory;
import indi.yuluo.springframework.beans.factory.BeanFactoryAware;
import indi.yuluo.springframework.beans.factory.BeanNameAware;
import indi.yuluo.springframework.beans.factory.DisposableBean;
import indi.yuluo.springframework.beans.factory.InitializingBean;
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
			// 执行bean的初始化方法和BeanPostProcessor的前置和后置方法
			bean = initializeBean(bean, beanName, beanDefinition);
		}
		catch (Exception e) {
			throw new BeansException("Instantiation of bean failed", e);
		}

		// 注册实现了DisposableBean 接口的Bean对象
		registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

		// 添加Bean实例 SCOPE_SINGLETON SCOPE_PROTOTYPE
		if (beanDefinition.isSingleton()) {
			addSingleton(beanName, bean);
		}

		return bean;
	}

	protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {

		// 非Singleton 类型的bean不执行销毁方法
		if (!beanDefinition.isSingleton()) {
			return;
		}

		if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
			registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
		}
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

	private Object initializeBean(Object bean, String beanName, BeanDefinition beanDefinition) {

		// invokeAwareMEthods
		if (bean instanceof Aware) {
			if (bean instanceof BeanFactoryAware) {
				((BeanFactoryAware) bean).setBeanFactory(this);
			}
			if (bean instanceof BeanClassLoaderAware) {
				((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
			}
			if (bean instanceof BeanNameAware) {
				((BeanNameAware) bean).setBeanName(beanName);
			}
		}

		// 1. 执行 BeanPostProcessor Before 处理
		Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

		try {
			invokeInitMethods(beanName, wrappedBean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
		}

		// 执行bean对象的初始化方法
		try {
			invokeInitMethods(beanName, wrappedBean, beanDefinition);
		} catch (Exception e) {
			throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
		}

		// 2. 执行 BeanPostProcessor After 处理
		wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
		return wrappedBean;

	}

	private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition)
			throws Exception {
		// 1. 实现接口 InitializingBean
		if (bean instanceof InitializingBean) {
			((InitializingBean) bean).afterPropertiesSet();
		}

		// 2. 注解配置 init-method {判断是为了避免二次执行初始化}
		String initMethodName = beanDefinition.getInitMethodName();
		if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean)) {
			Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
			if (initMethod == null) {
				throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
			}
			initMethod.invoke(bean);
		}
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
