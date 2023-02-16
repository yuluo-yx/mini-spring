package indi.yuluo.springframework.beans.factory;

/**
 *@author yuluo
 *@createTime 2023-01-30  12:34
 */

public interface InitializingBean {

	/**
	 * Bean 处理了属性填充后调用
	 */
	void afterPropertiesSet() throws Exception;

}
