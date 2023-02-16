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
import indi.yuluo.springframework.context.support.ClassPathXmlApplicationContext;
import indi.yuluo.springframework.core.io.DefaultResourceLoader;
import indi.yuluo.springframework.core.io.Resource;
import indi.yuluo.springframework.test.bean.UserDao;
import indi.yuluo.springframework.test.bean.UserService;
import indi.yuluo.springframework.test.common.MyBeanFactoryPostProcessor;
import indi.yuluo.springframework.test.common.MyBeanPostProcessor;
import org.junit.Before;
import org.junit.Test;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 */

public class ApiTest {

	/**
	 * 不使用应用上下文
	 */
	@Test
	public void test_BeanFactoryPostProcessorAndBeanPostProcessor(){
		// 1.初始化 BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2. 读取配置文件&注册Bean
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions("classpath:spring.xml");

		// 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
		MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
		beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

		// 4. Bean实例化之后，修改 Bean 属性信息
		MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
		beanFactory.addBeanPostProcessor(beanPostProcessor);

		// 5. 获取Bean对象调用方法
		UserService userService = beanFactory.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);
	}

	/**
	 * 使用应用上下文
	 */
	@Test
	public void test_xml() {
		// 1.初始化 BeanFactory
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

		// 2. 获取Bean对象调用方法
		UserService userService = applicationContext.getBean("userService", UserService.class);
		String result = userService.queryUserInfo();
		System.out.println("测试结果：" + result);
	}

}

