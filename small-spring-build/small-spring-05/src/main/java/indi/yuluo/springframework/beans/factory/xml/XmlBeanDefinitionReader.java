package indi.yuluo.springframework.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanReference;
import indi.yuluo.springframework.beans.factory.support.AbstractBeanfinitionReader;
import indi.yuluo.springframework.beans.factory.support.BeanDefinitionRegistry;
import indi.yuluo.springframework.core.io.Resource;
import indi.yuluo.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:24
 */

public class XmlBeanDefinitionReader extends AbstractBeanfinitionReader {

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		super(registry, resourceLoader);
	}

	/**
	 * 处理资源加载
	 * @param resource
	 * @throws BeansException
	 */
	@Override
	public void loadBeanDefinitions(Resource resource) throws BeansException {
		try {
			try (InputStream inputStream = resource.getInputStream()) {
				doLoadBeanDefinition(inputStream);
			}
		}
		catch (IOException | ClassNotFoundException e) {
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
		Resource resources = resourceLoader.getResource(location);

		loadBeanDefinitions(resources);
	}

	/**
	 * 主要负责解析 xml
	 * @param inputStream
	 * @throws ClassNotFoundException
	 */
	protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {

		Document document = XmlUtil.readXML(inputStream);
		Element root = document.getDocumentElement();
		NodeList childNodes = root.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			// 判断元素
			if (!(childNodes.item(i) instanceof Element bean)) {
				continue;
			}
			// 判断对象
			if (!"bean".equals(childNodes.item(i).getNodeName())) {
				continue;
			}
			// 解析标签
			String id = bean.getAttribute("id");
			String name = bean.getAttribute("name");
			String className = bean.getAttribute("class");
			// 获取 Class，方便获取类中的名称
			Class<?> clazz = Class.forName(className);
			// 优先级 id > name
			String beanName = StrUtil.isNotEmpty(id) ? id : name;
			if (StrUtil.isEmpty(beanName)) {
				beanName = StrUtil.lowerFirst(clazz.getSimpleName());
			}

			// 定义Bean
			BeanDefinition beanDefinition = new BeanDefinition(clazz);
			// 读取属性并填充
			for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
				if (!(bean.getChildNodes().item(j) instanceof Element)) {
					continue;
				}
				if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) {
					continue;
				}
				// 解析标签：property
				Element property = (Element) bean.getChildNodes().item(j);
				String attrName = property.getAttribute("name");
				String attrValue = property.getAttribute("value");
				String attrRef = property.getAttribute("ref");
				// 获取属性值：引入对象、值对象
				Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
				// 创建属性信息
				PropertyValue propertyValue = new PropertyValue(attrName, value);
				beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}
			if (getRegistry().containsBeanDefinition(beanName)) {
				throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
			}

			// 注册BeanDefinition
			getRegistry().registerBeanDefinition(beanName, beanDefinition);
		}

	}

}
