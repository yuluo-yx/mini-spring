package indi.yuluo.springframework.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuluo
 * @createTime 2023-02-06  13:01
 * @des 用于配置到 Class 类上的。除此之外 还有 Service、Controller，
 * 不过所有的处理方式基本一致，这里就只展示一个Component 即可
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

	String value() default "";

}
