package indi.yuluo.springframework.beans.factory;

import indi.yuluo.springframework.beans.BeansException;

/**
 * @author yuluo
 * @createTime 2023-02-07  20:21
 * @des null
 */

public interface ObjectFactory<T> {

	T getObject() throws BeansException;

}
