package indi.yuluo.springframework.context.support;

import java.util.Map;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.ConfigurableListableBeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanFactoryPostProcessor;
import indi.yuluo.springframework.beans.factory.config.BeanPostProcessor;
import indi.yuluo.springframework.context.ConfigurableApplicationContext;
import indi.yuluo.springframework.core.io.DefaultResourceLoader;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:53
 */

public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {

	/**
	 * 刷新容器方法实现
	 * @throws BeansException
	 */
	@Override
	public void refresh() throws BeansException {
		// 创建BeanFactory 并加载 BeanDefinition
		refreshBeanFactory();

		// 获取BeanFactory
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();

		// 在bean实例化之前执行，执行BeanFactoryPostProcessor(Invoke factory processor as beans in the context)
		invokeBeanFactoryPostProcessors(beanFactory);

		// BeanPostProcessor 需要提前于其他bean对象实例化之前执行注册操作
		registerBeanFactoryProcessors(beanFactory);

		// 提前实例化单例bean对象
		beanFactory.preInstantiateSingletons();
	}

	protected abstract void refreshBeanFactory() throws BeansException;

	protected abstract ConfigurableListableBeanFactory getBeanFactory();

	private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap =
				beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);

		for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	private void registerBeanFactoryProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap =
				beanFactory.getBeansOfType(BeanPostProcessor.class);

		for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		}
	}

	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	@Override
	public void close() {
		getBeanFactory().destroySingletons();
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return getBeanFactory().getBeansOfType(type);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return getBeanFactory().getBean(name);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		return getBeanFactory().getBean(name, args);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(name, requiredType);
	}
}
