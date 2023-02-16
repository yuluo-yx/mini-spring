package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Constructor;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 *@author yuluo
 *@createTime 2023-01-26  18:07
 * cglib实例化
 */

public class CglibSubClassingInstantiationStrategy implements InstantiationStrategy {

	@Override
	public Object instantiate(BeanDefinition beanDefinition, String beanName,
			Constructor ctor, Object[] args) throws BeansException {

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beanDefinition.getBeanClass());
		enhancer.setCallback(new NoOp() {
			@Override
			public int hashCode() {
				return super.hashCode();
			}
		});

		if (ctor == null) {
			return enhancer.create();
		}

		return enhancer.create(ctor.getParameterTypes(), args);

	}
}
