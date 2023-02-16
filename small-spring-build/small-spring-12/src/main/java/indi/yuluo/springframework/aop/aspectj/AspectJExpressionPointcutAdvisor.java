package indi.yuluo.springframework.aop.aspectj;

import indi.yuluo.springframework.aop.Pointcut;
import indi.yuluo.springframework.aop.PointcutAdvisor;
import indi.yuluo.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.aopalliance.aop.Advice;

/**
 * @author yuluo
 * @createTime 2023-02-04  17:53
 * @des AspectJExpressionPointcutAdvisor 实现了 PointcutAdvisor 接口，
 * 把切面 pointcut、拦截方法 advice 和具体的拦截表达式包装在一起
 */


public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

	// 切面
	private AspectJExpressionPointcut pointcut;
	// 具体的拦截方法
	private Advice advice;
	// 表达式
	private String expression;

	public void setExpression(String expression){
		this.expression = expression;
	}

	@Override
	public Pointcut getPointcut() {
		if (null == pointcut) {
			pointcut = new AspectJExpressionPointcut(expression);
		}
		return pointcut;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}

	public void setAdvice(Advice advice){
		this.advice = advice;
	}
}
