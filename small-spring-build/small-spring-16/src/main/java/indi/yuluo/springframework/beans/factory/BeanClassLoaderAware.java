package indi.yuluo.springframework.beans.factory;

/**
 *@author yuluo
 *@createTime 2023-02-01  21:45
 */
public interface BeanClassLoaderAware extends Aware{

	/**
	 * Callback that allows a bean to be aware of the bean{@link ClassLoader class loader};
	 * that is, the class loader used by the present bean factory to load bean classes.
	 * 实现此接口，既能感知到所属的 ClassLoader
	 */
	void setBeanClassLoader(ClassLoader classLoader);

}
