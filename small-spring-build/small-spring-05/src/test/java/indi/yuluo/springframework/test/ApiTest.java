package indi.yuluo.springframework.test;

import java.io.IOException;
import java.io.InputStream;

import cn.hutool.core.io.IoUtil;
import indi.yuluo.springframework.beans.PropertyValue;
import indi.yuluo.springframework.beans.PropertyValues;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.beans.factory.config.BeanReference;
import indi.yuluo.springframework.beans.factory.support.DefaultListableBeanFactory;
import indi.yuluo.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import indi.yuluo.springframework.core.io.DefaultResourceLoader;
import indi.yuluo.springframework.core.io.Resource;
import indi.yuluo.springframework.test.bean.UserDao;
import indi.yuluo.springframework.test.bean.UserService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class ApiTest {

	@Test
	public void test_BeanFactory() {
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2. UserDao 注册
		beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

		// 3. UserService 设置属性[uId、userDao]
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
		propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

		// 4. UserService 注入bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 5. UserService 获取bean
		UserService userService = (UserService) beanFactory.getBean("userService");
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);
	}

	private DefaultResourceLoader resourceLoader;

	@Before
	public void init() {
		resourceLoader = new DefaultResourceLoader();
	}

	@Test
	public void test_classpath() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);
		System.out.println(content);
	}

	@Test
	public void test_file() throws IOException {
		Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
		InputStream inputStream = resource.getInputStream();
		String content = IoUtil.readUtf8(inputStream);
		System.out.println(content);
	}

//	@Test
//	public void test_url() throws IOException {
//		Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/important.properties");
//		InputStream inputStream = resource.getInputStream();
//		String content = IoUtil.readUtf8(inputStream);
//		System.out.println(content);
//	}

	@Test
	public void test_xml() {
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2. 读取配置文件&注册Bean
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");

		// 3. 获取Bean对象调用方法
		UserService userService = beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);
	}

}

