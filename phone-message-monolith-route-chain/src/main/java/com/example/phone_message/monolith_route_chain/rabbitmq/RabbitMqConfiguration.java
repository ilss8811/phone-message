package com.example.phone_message.monolith_route_chain.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Egor Eryomin on 28.07.2022
 * @project phone-message
 */

@Configuration
public class RabbitMqConfiguration {

    @Value("${application.broker.request-rpc-queue}")
    private String requestRpcQueueName;

    @Value("${application.broker.request-default-queue}")
    private String requestDefaultQueueName;

    @Value("${application.broker.exchange}")
    private String exchangeName;

    @Value("${application.broker.rpc-routing-key}")
    private String rpcRoutingKey;

    @Value("${application.broker.default-routing-key}")
    private String defaultRoutingKey;

    @Bean
    public Queue phoneMessageRpcRequestQueue() {
        return new Queue(requestRpcQueueName, false, false, true);
    }

    @Bean
    public Queue phoneMessageDefaultRequestQueue() {
        return new Queue(requestDefaultQueueName, false, false, true);
    }

    @Bean
    public DirectExchange phoneMessageDirectExchange() {
        return new DirectExchange(exchangeName, false, true);
    }

    @Bean
    public Binding phoneRpcMessageBinding(DirectExchange directExchange, @Qualifier("phoneMessageRpcRequestQueue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with(rpcRoutingKey);
    }

    @Bean
    public Binding phoneDefaultMessageBinding(DirectExchange directExchange, @Qualifier("phoneMessageDefaultRequestQueue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with(defaultRoutingKey);
    }


}
