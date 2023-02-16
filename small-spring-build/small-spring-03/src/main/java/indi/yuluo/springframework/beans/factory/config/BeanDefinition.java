package indi.yuluo.springframework.beans.factory.config;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:14
 * 将Object替换为class，将Bean的实例化操作放到容器中进行处理
 */

public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
