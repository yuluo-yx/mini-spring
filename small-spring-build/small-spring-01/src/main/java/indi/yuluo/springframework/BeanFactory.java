package indi.yuluo.springframework;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuluo
 * @createTime 2023-01-25  21:48
 */

public class BeanFactory {

    private Map<String, BeanDefinition> beanDefiniationMap = new ConcurrentHashMap<String, BeanDefinition>();

    public Object getBean(String name) {
        return beanDefiniationMap.get(name).getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefiniationMap.put(name, beanDefinition);
    }

}
