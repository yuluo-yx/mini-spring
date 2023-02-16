package indi.yuluo.springframework.beans.factory;

/**
 *@author yuluo
 *@createTime 2023-01-30  12:34
 */

public interface DisposableBean {

	void destroy() throws Exception;

}
