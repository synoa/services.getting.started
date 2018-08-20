package de.synoa.getting.started.helloworld.configurations;

import de.synoa.getting.started.helloworld.App;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQ {

    @Autowired
    private PooledConnectionFactory connectionPool;

    private static final String QUEUE_BASE = "activemq:de.synoa.getting.started.helloworld.";

    private static final String QUEUE_BASE_NO_TX = "activemqNoTx:de.synoa.getting.started.helloworld.";

    public static final String QUEUE_PERSONS = QUEUE_BASE + "persons";

    public static final String QUEUE_PERSONS_NO_TX = QUEUE_BASE_NO_TX + "persons";

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
