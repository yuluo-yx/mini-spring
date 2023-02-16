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

}
