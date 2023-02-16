package indi.yuluo.springframework.beans.factory.support;

import java.util.HashMap;
import java.util.Map;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:16
 */

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

		if (beanDefinition == null) {
			throw new BeansException("No bean named '" + beanName + "'is defined");
		}

		return beanDefinition;
	}

}
