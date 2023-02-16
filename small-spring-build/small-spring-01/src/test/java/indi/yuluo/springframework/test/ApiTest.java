package indi.yuluo.springframework.test;

import indi.yuluo.springframework.BeanDefinition;
import indi.yuluo.springframework.BeanFactory;
import indi.yuluo.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * @author yuluo
 * @createTime 2023-01-25  21:52
 */

public class ApiTest {

    @Test
    public void testBeanFactory() {
        // 初始化BeanFactory
        BeanFactory factory = new BeanFactory();

        // 注册bean
        BeanDefinition definition = new BeanDefinition(new UserService());
        factory.registerBeanDefinition("userService", definition);

        // 获取bean
        UserService userService = (UserService) factory.getBean("userService");
        userService.queryUserInfo();
    }

}
