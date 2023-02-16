package indi.yuluo.springframework.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.BeanFactory;
import indi.yuluo.springframework.beans.factory.FactoryBean;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanPostProcessor;
import indi.yuluo.springframework.beans.factory.config.ConfigurableBeanFactory;
import indi.yuluo.springframework.util.ClassUtils;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:15
 * 抽象类定义模板方法
 *
 * 首先这里把 AbstractBeanFactory 原来继承的 DefaultSingletonBeanRegistry，
 * 修改 为继承 FactoryBeanRegistrySupport。因为需要扩展出创建 FactoryBean 对象的能力，
 * 所以这就想一个链条服务上，截出一个段来处理额外的服务，并把链条再链接上
 *
 *
 */

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

	/** ClassLoader to resolve bean class names with, if necessary */
	private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

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
		Object sharedInstance = getSingleton(name);

		if (sharedInstance != null) {

			// 如果是factoryBean，则需要调用FactoryBean#getObject
			return (T) getObjectForBeanInstance(sharedInstance, name);
		}

		BeanDefinition beanDefinition = getBeanDefinition(name);
		Object bean = createBean(name, beanDefinition, args);

		return (T) getObjectForBeanInstance(bean, name);
	}

	private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
		if (!(beanInstance instanceof FactoryBean)) {
			return beanInstance;
		}

		Object object = getCachedObjectForFactoryBean(beanName);

		if (object == null) {
			FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
			object = getObjectForBeanInstance(factoryBean, beanName);
		}

		return object;
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

	public ClassLoader getBeanClassLoader() {
		return this.beanClassLoader;
	}

}
