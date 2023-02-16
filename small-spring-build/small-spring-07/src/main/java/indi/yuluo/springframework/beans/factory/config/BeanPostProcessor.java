package indi.yuluo.springframework.beans.factory.config;

import indi.yuluo.springframework.beans.BeansException;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:51
 */

public interface BeanPostProcessor {

	/**
	 * 在 bean 对象执行初始化方法之前，执行此方法
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

	/**
	 * 在bean对象执行初始化方法之后，执行此方法
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
