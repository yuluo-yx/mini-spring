package indi.yuluo.springframework.aop.framework;

import indi.yuluo.springframework.aop.AdvisedSupport;

/**
 * @author yuluo
 * @createTime 2023-02-04  21:53
 * @des null
 */

public class ProxyFactory {

	private AdvisedSupport advisedSupport;

	public ProxyFactory(AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}

	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	private AopProxy createAopProxy() {
		if (advisedSupport.isProxyTargetClass()) {
			return new Cglib2AopProxy(advisedSupport);
		}

		return new JdkDynamicAopProxy(advisedSupport);
	}

}
