package indi.yuluo.springframework.beans.factory;

import java.io.IOException;
import java.util.Properties;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.PropertyValues;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanFactoryPostProcessor;
import indi.yuluo.springframework.core.io.DefaultResourceLoader;
import indi.yuluo.springframework.core.io.Resource;
import indi.yuluo.springframework.util.StringValueResolver;

/**
 * @author yuluo
 * @createTime 2023-02-06  12:54
 * @des 依赖于 BeanFactoryPostProcessor 在 Bean 生命周期的属性，
 * 可以在 Bean 对象实例化之前，改变属性信息。
 * 所以这里通过实现 BeanFactoryPostProcessor 接口，
 * 完成对配置文件的加载以及摘取占位符中的在属性文件里的配置。
 * 这样就可以把提取到的配置信息放置到属性配置中了
 */

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

	/**
	 * Default placeholder prefix: {@value}
	 */
	public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

	/**
	 * Default placeholder suffix: {@value}
	 */
	public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

	private String location;

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try {
			// 加载属性文件
			DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource resource = resourceLoader.getResource(location);

			// 占位符替换属性值
			Properties properties = new Properties();
			properties.load(resource.getInputStream());

			String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
			for (String beanName : beanDefinitionNames) {
				BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

				PropertyValues propertyValues = beanDefinition.getPropertyValues();
				for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
					Object value = propertyValue.getValue();
					if (!(value instanceof String)) continue;
					value = resolvePlaceholder((String) value, properties);
					propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), value));
				}
			}

			// 向容器中添加字符串解析器，供解析@Value注解使用
			StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(properties);
			/*
			在解析属性配置的类 PropertyPlaceholderConfigurer 中，最主要的其实就是这行 代码的操作
			beanFactory.addEmbeddedValueResolver(valueResolver) 这是把
			属性值写入到了 AbstractBeanFactory 的 embeddedValueResolvers 中
			*/
			beanFactory.addEmbeddedValueResolver(valueResolver);
		}
		catch (IOException e) {
			throw new BeansException("Could not load properties", e);
		}
	}

	private String resolvePlaceholder(String value, Properties properties) {
		String strVal = value;
		StringBuilder buffer = new StringBuilder(strVal);
		int startIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
		int stopIdx = strVal.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
		if (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
			String propKey = strVal.substring(startIdx + 2, stopIdx);
			String propVal = properties.getProperty(propKey);
			buffer.replace(startIdx, stopIdx + 1, propVal);
		}
		return buffer.toString();
	}

	private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

		private final Properties properties;

		public PlaceholderResolvingStringValueResolver(Properties properties) {
			this.properties = properties;
		}

		@Override
		public String resolveStringValue(String strVal) {
			return PropertyPlaceholderConfigurer.this.resolvePlaceholder(strVal, properties);
		}

	}

}
