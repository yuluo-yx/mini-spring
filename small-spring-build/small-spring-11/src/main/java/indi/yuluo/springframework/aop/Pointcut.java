package indi.yuluo.springframework.aop;

/**
 *@author yuluo
 *@createTime 2023-02-03  21:52
 *
 * 代理切面接口
 * 切入点接口，定义用于获取 ClassFilter、MethodMatcher 的两个类，这两个接口 获取都是切点表达式提供的内容
 */

public interface Pointcut {

	/**
	 * Return the ClassFilter for this pointcut. *
	 * @return the ClassFilter (never <code>null</code>)
	 */
	ClassFilter getClassFilter();

	/**
	 * Return the MethodMatcher for this pointcut
	 * @return the MethodMatcher
	 */
	MethodMatcher getMethodMatcher();

}
