package indi.yuluo.springframework.beans.factory;

/**
 *@author yuluo
 *@createTime 2023-02-01  22:26
 */

public interface FactoryBean<T> {

	T getObject() throws Exception;

	Class<?> getObjectType();

	boolean isSingleton();

}
