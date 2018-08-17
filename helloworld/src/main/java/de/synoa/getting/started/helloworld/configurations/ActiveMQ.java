package de.synoa.getting.started.helloworld.configurations;

import de.synoa.getting.started.helloworld.App;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class ActiveMQ {

    @Autowired
    private PooledConnectionFactory connectionPool;

    private static final String QUEUE_BASE = "activemq:{{activemq.queue.prefix}}.";

    private static final String TOPIC_BASE = "activemq:topic:{{activemq.queue.prefix}}.";

    private static final String QUEUE_BASE_NO_TX = "activemqNoTx:{{activemq.queue.prefix}}.";

    private static final String TOPIC_BASE_NO_TX = "activemqNoTx:topic:{{activemq.queue.prefix}}.";

    @Bean(name = "activemq")
    public ActiveMQComponent createActiveMQComponent() {

        JmsConfiguration jmsConfiguration = new JmsConfiguration(connectionPool);
        jmsConfiguration.setConcurrentConsumers(1);

        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConfiguration(jmsConfiguration);
        activeMQComponent.setTransacted(true);
        activeMQComponent.setClientId(App.class.getPackage().getName());

        return activeMQComponent;
    }

    @Bean(name = "activemqNoTx")
    public ActiveMQComponent createActiveMQComponentNoTransaction() {

        JmsConfiguration jmsConfiguration = new JmsConfiguration(connectionPool);
        jmsConfiguration.setConcurrentConsumers(1);

        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConfiguration(jmsConfiguration);
        activeMQComponent.setTransacted(false);
        activeMQComponent.setClientId(App.class.getPackage().getName());

        return activeMQComponent;
    }
}
