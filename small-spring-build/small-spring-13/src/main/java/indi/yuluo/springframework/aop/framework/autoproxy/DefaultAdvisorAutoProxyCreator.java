package indi.yuluo.springframework.aop.framework.autoproxy;

import java.util.Collection;

import indi.yuluo.springframework.aop.AdvisedSupport;
import indi.yuluo.springframework.aop.Advisor;
import indi.yuluo.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import indi.yuluo.springframework.aop.ClassFilter;
import indi.yuluo.springframework.aop.Pointcut;
import indi.yuluo.springframework.aop.TargetSource;
import indi.yuluo.springframework.aop.framework.ProxyFactory;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.BeanFactory;
import indi.yuluo.springframework.beans.factory.BeanFactoryAware;
import indi.yuluo.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import indi.yuluo.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author yuluo
 * @createTime 2023-02-04  21:59
 * @des null
 */

public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {

		if (isInfrastructureClass(beanClass)) return null;

		Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

		for (AspectJExpressionPointcutAdvisor advisor : advisors) {
			ClassFilter classFilter = advisor.getPointcut().getClassFilter();
			if (!classFilter.matches(beanClass)) continue;

			AdvisedSupport advisedSupport = new AdvisedSupport();

			TargetSource targetSource = null;
			try {
				targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
			advisedSupport.setTargetSource(targetSource);
			advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
			advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
			advisedSupport.setProxyTargetClass(false);

			return new ProxyFactory(advisedSupport).getProxy();

		}

		return null;
	}

	private boolean isInfrastructureClass(Class<?> beanClass) {
		return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}
