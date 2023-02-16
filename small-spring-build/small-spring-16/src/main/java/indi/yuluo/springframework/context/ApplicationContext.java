package indi.yuluo.springframework.context;

import indi.yuluo.springframework.beans.factory.HierarchicalBeanFactory;
import indi.yuluo.springframework.beans.factory.ListableBeanFactory;
import indi.yuluo.springframework.core.io.ResourceLoader;

/**
 * @author yuluo
 * @createTime 2023-01-30  10:52
 * 中央接口
 */

public interface ApplicationContext
		extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
