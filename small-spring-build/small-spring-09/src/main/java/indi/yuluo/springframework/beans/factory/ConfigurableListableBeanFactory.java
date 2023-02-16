package indi.yuluo.springframework.beans.factory;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.AutowireCapableBeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:31
 * 提供分析和修改 Bean 以及预先实例化的操作接口
 */

public interface ConfigurableListableBeanFactory extends
		ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

	BeanDefinition getBeanDefinition(String beanName) throws BeansException;

	void preInstantiateSingletons() throws BeansException;

}
