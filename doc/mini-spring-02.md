# 实现 Bean 的定义，注册和获取

其实编码方式主要依托于：**接口定义、类实现接口、抽象类 实现接口、继承类、继承抽象类**，而这些操作方式可以很好的隔离开每个类的 基础功能、通用功能和业务功能，当类的职责清晰后，你的整个设计也会变得
容易扩展和迭代。

## 目标

把 Bean 的创建交给容器，而不是我们在调用时候传递一个实例化好的 Bean 对象，另外还需要考虑单例对象，在对象的二次获取时是可以从内 存中获取对象的。此外不仅要实现功能还需要完善基础容器框架的类结构体，
否则将来就很难扩容进去其他的功能了

## 设计

完善 Spring Bean 容器，非常重要的一点是在 Bean 注册的时候只注册一个类信息，而不会直接把实例化信息注册到 Spring 容器中，就需要修改 BeanDefinition 中的属性 Object 为 Class，接下来需要做的就是在获取 Bean 的时候处理 Bean 对象的实例化操作以及判断当前单例对象是否已经缓存到容器中。

- 定义 BeanFactory 工厂

  提供 Bean 的获取方法 getBean(String name)，之后这个 Bean 工厂接口由抽象类 AbstractBeanFactory 实现。这样使用模板模式的设计方式，可以统一收口通用核 心方法的调用逻辑和标准定义，也就很好的控制了后续的实现者不用关心调用逻
  辑，按照统一方式执行。那么类的继承者只需要关心具体方法的逻辑实现即可。

- AbstractAutowireCapableBeanFactory 实现

  因为 AbstractAutowireCapableBeanFactory 本身也是一个抽象类，所以它只会实现属于 自己的抽象方法，其他抽象方法由继承 AbstractAutowireCapableBeanFactory 的 类实现。这里就体现了类实现过程中的各司其职，你只需要关心属于你的内容，不
  是你的内容，不要参与

- 单例 SingletonBeanRegistry 的接口 定义实现

  关于单例 SingletonBeanRegistry 的接口 定义实现，而 DefaultSingletonBeanRegistry 对接口实现后，会被抽象类 AbstractBeanFactory 继承。现在 AbstractBeanFactory 就是一个非常完整且强大的
  抽象类了，也能非常好的体现出它对模板模式的抽象定义。

## 实现

BeanDefinition 定义

```java
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
```

单例注册接口定义和实现

```java
public interface SingletonBeanRegistry {

	Object getSingleton(String beanName);

}
```

```java
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	private Map<String, Object> singletonObjects = new HashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}

	protected void addSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
	}

}
```

在 DefaultSingletonBeanRegistry 中主要实现 getSingleton 方法，同时实现了一个 受保护的 addSingleton 方法，这个方法可以被继承此类的其他类调用。包括：AbstractBeanFactory 以及继承的 DefaultListableBeanFactory 调用

抽象类定义模板方法

```java
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	@Override
	public Object getBean(String name) throws BeansException {
			Object bean = getSingleton(name);
			if (bean != null) {
				return bean;
			}

			BeanDefinition beanDefinition = getBeanDefinition(name);
			return createBean(name, beanDefinition);
	}

	protected abstract BeanDefinition getBeanDefinition(String name) throws BeansException;

	protected abstract Object createBean(String name, BeanDefinition beanDefinition) throws BeansException;

}
```

实例化 Bean 类 （AbstractAutowireCapableBeanFactory）

```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

	@Override
	protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {

		Object bean = null;
		try {
			bean = beanDefinition.getBeanClass().newInstance();
		}
		catch (InstantiationException | IllegalAccessException e) {
			throw new BeansException("Instantiation of bean failed", e);
		}
		
        // 在处理完 Bean 对象的实例化后，直接调用 addSingleton 方法存放到单例对象的缓存中去
		addSingleton(beanName, bean);

		return bean;
	}
}
```

核心类实现(DefaultListableBeanFactory)

```java
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry{

	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

		if (beanDefinition == null) {
			throw new BeansException("No bean named '" + beanName + "'is defined");
		}

		return beanDefinition;
	}

}
```

DefaultListableBeanFactory 继承了 AbstractAutowireCapableBeanFactory 类，也 就具备了接口 BeanFactory 和 AbstractBeanFactory 等一连串的功能实现。所以有 时候你会看到一些类的强转，调用某些方法，也是因为你强转的类实现接口或继承
了某些类。

除此之外这个类还实现了接口 BeanDefinitionRegistry 中的 registerBeanDefinition(String beanName, BeanDefinition beanDefinition) 方法，当 然你还会看到一个 getBeanDefinition 的实现，这个方法我们文中提到过它是抽象类 AbstractBeanFactory 中定义的抽象方法。

## 测试

UserService

```java
public class UserService {

	public void queryUserInfo() {
		System.out.println("查询用户信息！");
	}

}

// 如下 UserService 报错 暂时不能实例化带有属性的 Bean 对象
public class UserService {

	private String name;

	public UserService(String name) {
		this.name = name;
	}

	public void queryUserInfo() {
		System.out.println("查询用户信息！");
	}

}
```

ApiTest

```java
public class ApiTest {

	@Test
	public void testBeanFactory() {
		// 初始化BeanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 注册bean
		BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
		beanFactory.registerBeanDefinition("userService", beanDefinition);

		// 第一次获取bean
		UserService userService = (UserService) beanFactory.getBean("userService");
		System.out.println(userService.toString());
		userService.queryUserInfo();

		// 第二次获取bean from Singleton
		UserService serviceSingleton = (UserService) beanFactory.getBean("userService");
		System.out.println(serviceSingleton.toString());
		serviceSingleton.queryUserInfo();
	}

}
```

注意：在这里面有一个小坑，不能实例化带有属性的 Bean 会报异常！可以思考一下为什么以及怎么处理？