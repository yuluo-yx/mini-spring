package indi.yuluo.springframework.beans.factory;

import indi.yuluo.springframework.beans.BeansException;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:16
 * bean工厂接口
 */

public interface BeanFactory {

	Object getBean(String beanName) throws BeansException;

}
