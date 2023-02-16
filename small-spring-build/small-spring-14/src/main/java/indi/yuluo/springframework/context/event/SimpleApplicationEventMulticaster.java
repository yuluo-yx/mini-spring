package indi.yuluo.springframework.context.event;

import indi.yuluo.springframework.beans.factory.BeanFactory;
import indi.yuluo.springframework.context.ApplicationEvent;
import indi.yuluo.springframework.context.ApplicationListener;

/**
 * @author yuluo
 * @createTime 2023-02-02  14:51
 */

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

	public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
		setBeanFactory(beanFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void multicastEvent(final ApplicationEvent event) {
		for (final ApplicationListener listener : getApplicationListeners(event)) {
			listener.onApplicationEvent(event);
		}
	}

}
