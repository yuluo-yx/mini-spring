package indi.yuluo.springframework.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanReference;
import indi.yuluo.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import indi.yuluo.springframework.beans.factory.support.BeanDefinitionRegistry;
import indi.yuluo.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import indi.yuluo.springframework.core.io.Resource;
import indi.yuluo.springframework.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:24
 */

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		super(registry, resourceLoader);
	}

	@Override
	public void loadBeanDefinitions(Resource resource) throws BeansException {
		try {
			try (InputStream inputStream = resource.getInputStream()) {
				doLoadBeanDefinitions(inputStream);
			}
		}
		catch (IOException | ClassNotFoundException | DocumentException e) {
			throw new BeansException("IOException parsing XML document from " + resource, e);
		}
	}

	@Override
	public void loadBeanDefinitions(Resource... resources) throws BeansException {
		for (Resource resource : resources) {
			loadBeanDefinitions(resource);
		}
	}

	@Override
	public void loadBeanDefinitions(String location) throws BeansException {
		ResourceLoader resourceLoader = getResourceLoader();
		Resource resource = resourceLoader.getResource(location);
		loadBeanDefinitions(resource);
	}

	@Override
	public void loadBeanDefinitions(String... locations) throws BeansException {
		for (String location : locations) {
			loadBeanDefinitions(location);
		}
	}

	protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();

		// ?????? context:component-scan ??????????????????????????????????????????????????????????????? BeanDefinition
		Element componentScan = root.element("component-scan");
		if (null != componentScan) {
			String scanPath = componentScan.attributeValue("base-package");
			if (StrUtil.isEmpty(scanPath)) {
				throw new BeansException("The value of base-package attribute can not be empty or null");
			}
			scanPackage(scanPath);
		}

		List<Element> beanList = root.elements("bean");
		for (Element bean : beanList) {

			String id = bean.attributeValue("id");
			String name = bean.attributeValue("name");
			String className = bean.attributeValue("class");
			String initMethod = bean.attributeValue("init-method");
			String destroyMethodName = bean.attributeValue("destroy-method");
			String beanScope = bean.attributeValue("scope");

			// ?????? Class??????????????????????????????
			Class<?> clazz = Class.forName(className);
			// ????????? id > name
			String beanName = StrUtil.isNotEmpty(id) ? id : name;
			if (StrUtil.isEmpty(beanName)) {
				beanName = StrUtil.lowerFirst(clazz.getSimpleName());
			}

			// ??????Bean
			BeanDefinition beanDefinition = new BeanDefinition(clazz);
			beanDefinition.setInitMethodName(initMethod);
			beanDefinition.setDestroyMethodName(destroyMethodName);

			if (StrUtil.isNotEmpty(beanScope)) {
				beanDefinition.setScope(beanScope);
			}

			List<Element> propertyList = bean.elements("property");
			// ?????????????????????
			for (Element property : propertyList) {
				// ???????????????property
				String attrName = property.attributeValue("name");
				String attrValue = property.attributeValue("value");
				String attrRef = property.attributeValue("ref");
				// ??????????????????????????????????????????
				Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
				// ??????????????????
				PropertyValue propertyValue = new PropertyValue(attrName, value);
				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}
			if (getRegistry().containsBeanDefinition(beanName)) {
				throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
			}
			// ?????? BeanDefinition
			getRegistry().registerBeanDefinition(beanName, beanDefinition);
		}
	}

	private void scanPackage(String scanPath) {
		String[] basePackages = StrUtil.splitToArray(scanPath, ',');
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
		scanner.doScan(basePackages);
	}

}
