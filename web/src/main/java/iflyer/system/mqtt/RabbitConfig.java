package iflyer.system.mqtt;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 使用注解代替xml的标准配置方式
 * 1、表示将该pojo实例化到spring容器中 相当于<bean id="" class=""/>
 * 与@Bean不同的是 该注解是被用在要被自动扫描和装配的类上
 * 而@Bean主要被用在方法上，来显式声明要用生成的类
 */
@Component
//在spring初始化时初始化该类中的配置
@Configuration
//加载指定的属性文件
@PropertySource("classpath:application.properties")
public class RabbitConfig {

    @Value("${spring.rabbitmq.default.address}")
    public String address;
    @Value("${spring.rabbitmq.default.username}")
    public String username;
    @Value("${spring.rabbitmq.default.password}")
    public String password;

    /**2、定义Bean，并提供Bean的实例化逻辑*/
    @Bean(name = "rabbit.connectionFactory")
    public ConnectionFactory firstConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        /**[3、手动赋值]*/
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean(name = "rabbitAdmin")
    @Primary
    public RabbitAdmin getGmClubRabbitAdmin(@Qualifier("rabbit.connectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue testQueue() {
        return new Queue("test_queue");
    }
}