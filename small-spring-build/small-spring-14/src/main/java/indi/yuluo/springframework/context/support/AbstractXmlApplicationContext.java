package indi.yuluo.springframework.context.support;

import indi.yuluo.springframework.beans.factory.support.DefaultListableBeanFactory;
import indi.yuluo.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 *@author yuluo
 *@createTime 2023-01-30  10:53
 */

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
		String[] configLocations = getConfigLocations();

		if (configLocations != null) {
			beanDefinitionReader.loadBeanDefinitions(configLocations);
		}

	}

	protected abstract String[] getConfigLocations();

}
