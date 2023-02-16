package indi.yuluo.springframework.context.support;

import java.util.Collection;
import java.util.Map;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.ConfigurableListableBeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanFactoryPostProcessor;
import indi.yuluo.springframework.beans.factory.config.BeanPostProcessor;
import indi.yuluo.springframework.context.ApplicationEvent;
import indi.yuluo.springframework.context.ApplicationListener;
import indi.yuluo.springframework.context.ConfigurableApplicationContext;
import indi.yuluo.springframework.context.event.ApplicationEventMulticaster;
import indi.yuluo.springframework.context.event.ContextClosedEvent;
import indi.yuluo.springframework.context.event.ContextRefreshedEvent;
import indi.yuluo.springframework.context.event.SimpleApplicationEventMulticaster;
import indi.yuluo.springframework.core.io.DefaultResourceLoader;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:53
 */

public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {

	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

	private ApplicationEventMulticaster applicationEventMulticaster;

	/**
	 * 刷新容器方法实现
	 * @throws BeansException
	 */
	@Override
	public void refresh() throws BeansException {
		// 1 创建BeanFactory 并加载 BeanDefinition
		refreshBeanFactory();

		// 2 获取BeanFactory
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();

		// 3 添加ApplicationContextAwareProcessor, 让继承自ApplicationContextAware的Bean对象都能感知所属的ApplicationContext
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

		// 4 在bean实例化之前执行，执行BeanFactoryPostProcessor(Invoke factory processor as beans in the context)
		invokeBeanFactoryPostProcessors(beanFactory);

		// 5 BeanPostProcessor 需要提前于其他bean对象实例化之前执行注册操作
		registerBeanFactoryProcessors(beanFactory);

		// 7 初始化事件发布者
		initApplicationEventMulticaster();

		// 8 注册事件监听器
		registerListeners();

		// 6 提前实例化单例bean对象
		beanFactory.preInstantiateSingletons();

		// 9 发布容器刷新完成事件
		finishRefresh();

	}

	private void finishRefresh() {
		publishEvent(new ContextRefreshedEvent(this));
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		applicationEventMulticaster.multicastEvent(event);
	}

	private void registerListeners() {
		Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
		for (ApplicationListener listener : applicationListeners) {
			applicationEventMulticaster.addApplicationListener(listener);
		}
	}

	private void initApplicationEventMulticaster() {
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
		beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
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

		// 发布容器关闭事件
		publishEvent(new ContextClosedEvent(this));

		// 执行销毁单例bean的销毁方法
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

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		return getBeanFactory().getBean(requiredType);
	}

}
