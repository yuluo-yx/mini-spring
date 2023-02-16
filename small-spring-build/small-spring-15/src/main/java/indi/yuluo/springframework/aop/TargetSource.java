package indi.yuluo.springframework.aop;

import indi.yuluo.springframework.util.ClassUtils;

/**
 *@author yuluo
 *@createTime 2023-02-03  21:52
 * 被代理的目标对象
 */

public class TargetSource {

	private final Object target;

	public TargetSource(Object target) {
		this.target = target;
	}

	/**
	 * Return the type of targets returned by this {@link TargetSource}.
	 * <p>Can return <code>null</code>, although certain usages of a
	 * <code>TargetSource</code> might just work with a predetermined
	 * target class.
	 * @return the type of targets returned by this {@link TargetSource}
	 */
	public Class<?>[] getTargetClass(){

		Class<?> clazz = this.target.getClass();
		clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

		return clazz.getInterfaces();
	}

	/**
	 * Return a target instance. Invoked immediately before the
	 * AOP framework calls the "target" of an AOP method invocation.
	 * @return the target object, which contains the joinpoint
	 * @throws Exception if the target object can't be resolved
	 */
	public Object getTarget(){
		return this.target;
	}

}
