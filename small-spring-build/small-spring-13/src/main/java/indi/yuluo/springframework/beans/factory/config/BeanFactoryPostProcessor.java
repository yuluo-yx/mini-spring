package indi.yuluo.springframework.beans.factory.config;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:51
 */

public interface BeanFactoryPostProcessor {

	/**
	 * 在所有的BeanDefinition加载完成后，实例化Bean对象之前，提供修改BeanDefinition属性的机制
	 * @param beanFactory ConfigurableListableBeanFactory
	 * @throws BeansException 异常
	 */
	void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
