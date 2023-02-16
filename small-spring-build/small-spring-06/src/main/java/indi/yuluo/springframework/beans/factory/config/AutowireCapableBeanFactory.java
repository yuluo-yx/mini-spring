package indi.yuluo.springframework.beans.factory.config;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.BeanFactory;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:21
 * 一个自动化处理 Bean 工厂配置的接口
 */

public interface AutowireCapableBeanFactory extends BeanFactory {

	/**
	 * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

	/**
	 * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
	 *
	 * @param existingBean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
