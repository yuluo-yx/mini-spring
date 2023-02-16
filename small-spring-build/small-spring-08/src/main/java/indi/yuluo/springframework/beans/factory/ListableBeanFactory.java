package indi.yuluo.springframework.beans.factory;

import java.util.Map;

import indi.yuluo.springframework.beans.BeansException;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:31
 * 是一个扩展 Bean 工厂接口的接口
 */

public interface ListableBeanFactory extends BeanFactory{

	/**
	 * 按照类型返回 Bean 实例
	 * @param type
	 * @param <T>
	 * @return
	 * @throws BeansException
	 */
	<T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

	/**
	 * Return the names of all beans defined in this registry.
	 *
	 * 返回注册表中所有的Bean名称
	 */
	String[] getBeanDefinitionNames();

}
