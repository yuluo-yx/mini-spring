package indi.yuluo.springframework.aop;

/**
 * @author yuluo
 * @createTime 2023-02-04  17:50
 * @des null
 */

public interface PointcutAdvisor extends Advisor{

	/**
	 * Get the Pointcut that drives this advisor.
	 */
	Pointcut getPointcut();

}
