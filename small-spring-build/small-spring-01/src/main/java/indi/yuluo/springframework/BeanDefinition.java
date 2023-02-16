package indi.yuluo.springframework;

/**
 * @author yuluo
 * @createTime 2023-01-25  21:46
 */

public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
