package indi.yuluo.springframework.aop;

import java.lang.reflect.Method;

/**
 *@author yuluo
 *@createTime 2023-02-03  21:52
 * 方法匹配，找到表达式范围内匹配下的目标类和方法
 */

public interface MethodMatcher {

	/**
	 * Perform static checking whether the given method matches. If this
	 * @param method
	 * @param targetClass
	 * @return
	 */
	boolean matches(Method method, Class<?> targetClass);

}
