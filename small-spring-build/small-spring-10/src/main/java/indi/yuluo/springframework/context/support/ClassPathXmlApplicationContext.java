package indi.yuluo.springframework.context.support;

import indi.yuluo.springframework.beans.BeansException;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:54
 */

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

	private String[] configLocations;

	public ClassPathXmlApplicationContext() {
	}

	/**
	 * 从 XML 中加载 BeanDefinition，并刷新上下文
	 *
	 * @param configLocations
	 * @throws BeansException
	 */
	public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
		this(new String[]{configLocations});
	}

	/**
	 * 从 XML 中加载 BeanDefinition，并刷新上下文
	 * @param configLocations
	 * @throws BeansException
	 */
	public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
		this.configLocations = configLocations;
		refresh();
	}

	@Override
	protected String[] getConfigLocations() {
		return configLocations;
	}
}
