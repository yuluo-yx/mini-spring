package indi.yuluo.springframework.beans.factory.support;

import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:16
 *
 * 注册bean
 *
 */

public interface BeanDefinitionRegistry {

	void registerBeanDefinition(String beanName, BeanDefinition definition);

}
