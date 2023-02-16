package indi.yuluo.springframework.beans.factory;

/**
 *@author yuluo
 *@createTime 2023-02-01  21:45
 */

public interface BeanNameAware extends Aware {

	/**
	 * Interface to be implemented by beans that want to be aware of their bean name in a bean factory.
	 * 实现此接口，既能感知到所属的 BeanName
	 * @param name
	 */
	void setBeanName(String name);

}
