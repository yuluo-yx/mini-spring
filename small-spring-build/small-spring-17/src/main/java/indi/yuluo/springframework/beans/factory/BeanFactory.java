package indi.yuluo.springframework.beans.factory;

import indi.yuluo.springframework.beans.BeansException;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:16
 * bean工厂接口
 */

public interface BeanFactory {

	Object getBean(String beanName) throws BeansException;

	// 新增一个含有入参信息的getBean方法，可以传递入参给构造函数实例化
	Object getBean(String beanName, Object... args) throws BeansException;

	// 加入按类型获取bean对象的方法
	<T> T getBean(String name, Class<T> requiredType) throws BeansException;

	<T> T getBean(Class<T> requiredType) throws BeansException;

}
