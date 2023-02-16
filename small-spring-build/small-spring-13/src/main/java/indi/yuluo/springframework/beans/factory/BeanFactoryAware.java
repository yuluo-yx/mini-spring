package indi.yuluo.springframework.beans.factory;

import java.beans.Beans;

import indi.yuluo.springframework.beans.BeansException;

/**
 *@author yuluo
 *@createTime 2023-02-01  21:45
 */

public interface BeanFactoryAware extends Aware {

	/**
	 * Interface to be implemented by beans that wish to be aware of their owning {@link BeanFactory}.
	 * 实现此接口，既能感知到所属的 BeanFactory
	 * @param beanFactory
	 * @throws BeansException
	 */
	void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
