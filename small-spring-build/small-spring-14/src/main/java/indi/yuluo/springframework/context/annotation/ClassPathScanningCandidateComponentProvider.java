package indi.yuluo.springframework.context.annotation;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.hutool.core.util.ClassUtil;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.stereotype.Component;

/**
 * @author yuluo
 * @createTime 2023-02-06  13:03
 * @des 这里先要提供一个可以通过配置路径 basePackage=cn.bugstack.springframework.test.bean，
 * 解析出 classes 信息的工具方法 findCandidateComponents，通过这个方法就可以扫描到
 * 所有 @Component 注解的 Bean 对象了
 */

public class ClassPathScanningCandidateComponentProvider {

	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<>();
		Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);

		for (Class<?> aClass : classes) {
			candidates.add(new BeanDefinition(aClass));
		}

		return candidates;
	}

}
