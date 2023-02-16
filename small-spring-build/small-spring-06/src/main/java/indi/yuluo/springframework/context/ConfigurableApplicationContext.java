package indi.yuluo.springframework.context;

import indi.yuluo.springframework.beans.BeansException;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:52
 */

public interface ConfigurableApplicationContext extends ApplicationContext {

	/**
	 * 刷新容器
	 * @throws BeansException
	 */
	void refresh() throws BeansException;



}
