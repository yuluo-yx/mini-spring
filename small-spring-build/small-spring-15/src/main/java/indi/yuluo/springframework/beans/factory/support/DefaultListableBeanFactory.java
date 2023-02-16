package indi.yuluo.springframework.beans.factory.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.ConfigurableListableBeanFactory;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:16
 */

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return beanDefinitionMap.containsKey(beanName);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		Map<String, T> result = new HashMap<>();
		beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			Class beanClass = beanDefinition.getBeanClass();
			if (type.isAssignableFrom(beanClass)) {
				result.put(beanName, (T) getBean(beanName));
			}
		});
		return result;
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return beanDefinitionMap.keySet().toArray(new String[0]);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
		if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
		return beanDefinition;
	}

	@Override
	public void preInstantiateSingletons() throws BeansException {
		beanDefinitionMap.keySet().forEach(this::getBean);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		List<String> beanNames = new ArrayList<>();
		for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
			Class beanClass = entry.getValue().getBeanClass();
			if (requiredType.isAssignableFrom(beanClass)) {
				beanNames.add(entry.getKey());
			}
		}
		if (1 == beanNames.size()) {
			return getBean(beanNames.get(0), requiredType);
		}

		throw new BeansException(requiredType + "expected single bean but found " + beanNames.size() + ": " + beanNames);
	}

}
