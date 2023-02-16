package indi.yuluo.springframework.beans.factory.config;

/**
 *@author yuluo
 *@createTime 2023-01-26  20:11
 * 类引用
 */

public class BeanReference {

	private final String beanName;

	public BeanReference(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return beanName;
	}
}
