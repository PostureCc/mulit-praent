package iflyer.system.mqtt;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
//@PropertySource("classpath:application.properties")
public class MQServiceProduceInit {

    @Value("${spring.rabbitmq.exchange.queue.names}")
    String queueNames;

    @Autowired
    @Qualifier("rabbitAdmin")
    private RabbitAdmin rabbitAdmin;

    @Autowired
    @Qualifier("rabbit.connectionFactory")
    private ConnectionFactory connectionFactory;

    /**四、配置消息发送模板*/
    @Bean(name = "messageTemplate")
    public RabbitTemplate getMessageTemplate() {
        Queue queue;
        for (String queueName : queueNames.split(",")) {
            queue = new Queue(queueName, true, false, false);
            rabbitAdmin.declareQueue(queue);
        }
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());
        return rabbitTemplate;
    }

}