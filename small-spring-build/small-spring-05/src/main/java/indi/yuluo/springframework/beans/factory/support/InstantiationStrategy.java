package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Constructor;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 *@author yuluo
 *@createTime 2023-01-26  18:08
 * 实例化策略接口
 */

public interface InstantiationStrategy {

	// Constructor 是为了获取符合入参信息相对应的构造函数
	Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
