package indi.yuluo.springframework.context;

import indi.yuluo.springframework.beans.BeansException;

/**
 *@author yuluo
 *@createTime 2023-02-01  21:47
 */

public interface ApplicationContextAware {

	/**
	 * Interface to be implemented by any object that wishes to be notifiedof the {@link ApplicationContext}
	 * that it runs in.
	 * 实现此接口，既能感知到所属的 ApplicationContext
	 * @param applicationContext
	 * @throws BeansException
	 */
	void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
