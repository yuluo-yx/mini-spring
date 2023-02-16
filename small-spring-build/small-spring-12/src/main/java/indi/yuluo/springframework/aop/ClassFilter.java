package indi.yuluo.springframework.aop;

/**
 * @author yuluo
 * @createTime 2023-02-03  21:52
 * @des Should the pointcut apply to the given interface or target class?
 */

public interface ClassFilter {

	/**
	 * Should the pointcut apply to the given interface or target class?
	 * @param clazz
	 * @return
	 */
	boolean matches(Class<?> clazz);

}
