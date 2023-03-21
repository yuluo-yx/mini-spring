## 创建简单的Spring Bean容器

Spring 包含并管理应用对象的配置和生命周期，可以理解为一种用于承载对象的容器。

如果一个bean对象被交给 Spring 容器进行管理，那么这个bean容器对象就应该以类似零散零件的方式被拆解然后放到Bean的定义中去。等同于一种解耦合操作，由 Spring 更加轻松的管理，比如处理循环依赖等操作！

当一个Bean对象被定义存放之后，再由 Spring 统一进行装配和管理，这个过程包含 Bean 的初始化，属性填充等，最终就可以得到一个完整的 Bean 实例对象。

### 具体设计

凡是可以存放数据的具体数据结构实现，都可以称之为容器。

在 Spring 的 Bean容器场景下，使用**HashMap是最合适的**。

- 定义：BeanDefinition，这个类中会包括 `singleton, prototype, BeanClassName`等。在01章节中，我们只顶一个 Object 类型用于存放对象。

- 注册：此过程中，就相当于将 Bean 对象放到了HashMap容器中去。只不过现在的HashMap容器存放的是定义了 Bean 的对象信息。
- 获取：使用 Bean 的name属性获取对象。
- BeanFactory：代表 Bean 对象的工厂，可以存放 Bean 定义到Map容器中以及获取 Bean 的操作！

### 编码

- BeanDefinition

  ```java
  public class BeanDefinition {
  
      private Object bean;
  
      public BeanDefinition(Object bean) {
          this.bean = bean;
      }
  
      public Object getBean() {
          return bean;
      }
  
  }
  ```

- BeanFactory

  ```java
  public class BeanFactory {
  
      private Map<String, BeanDefinition> beanDefiniationMap = new ConcurrentHashMap<String, BeanDefinition>();
  
      public Object getBean(String name) {
          return beanDefiniationMap.get(name).getBean();
      }
  
      public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
          beanDefiniationMap.put(name, beanDefinition);
      }
  
  }
  ```

- 单元测试

  ```java
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
  
  class UserService {
  
      public void queryUserInfo() {
          System.out.println("查询用户信息");
      }
  
  }
  ```

  