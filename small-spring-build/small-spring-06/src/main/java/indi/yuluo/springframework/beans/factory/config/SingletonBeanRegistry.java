package indi.yuluo.springframework.beans.factory.config;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:14
 * 定义获取单例对象的接口
 */

public interface SingletonBeanRegistry {

	Object getSingleton(String beanName);

}
