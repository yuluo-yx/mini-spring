package indi.yuluo.springframework.beans.factory.config;

import indi.yuluo.springframework.beans.factory.HierarchicalBeanFactory;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:21
 * 可获取 BeanPostProcessor、BeanClassLoader 等的一个 配置化接口
 */

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	String SCOPE_SINGLETON = "singleton";

	String SCOPE_PROTOTYPE = "prototype";

}
