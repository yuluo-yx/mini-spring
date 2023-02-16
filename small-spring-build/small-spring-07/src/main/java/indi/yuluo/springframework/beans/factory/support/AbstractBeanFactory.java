package indi.yuluo.springframework.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.BeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanPostProcessor;
import indi.yuluo.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:15
 * 抽象类定义模板方法
 */

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {


	/** BeanPostProcessors to apply in createBean */
	private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

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

	@Override
	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
		this.beanPostProcessors.remove(beanPostProcessor);
		this.beanPostProcessors.add(beanPostProcessor);
	}

	/**
	 * Return the list of BeanPostProcessors that will get applied
	 * to beans created with this factory.
	 */
	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}

}
