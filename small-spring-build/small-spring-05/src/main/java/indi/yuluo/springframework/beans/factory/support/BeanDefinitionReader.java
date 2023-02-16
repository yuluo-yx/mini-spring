package indi.yuluo.springframework.beans.factory.support;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.core.io.Resource;
import indi.yuluo.springframework.core.io.ResourceLoader;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:23
 * 定义读取Bean接口
 * getRegistry()、getResourceLoader()，都是用于提供给后面三个方法 的工具，加载和注册，
 * 这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法
 */

public interface BeanDefinitionReader {


	BeanDefinitionRegistry getRegistry();

	ResourceLoader getResourceLoader();

	void loadBeanDefinitions(Resource resource) throws BeansException;

	void loadBeanDefinitions(Resource... resource) throws BeansException;

	void loadBeanDefinitions(String location) throws BeansException;
}
