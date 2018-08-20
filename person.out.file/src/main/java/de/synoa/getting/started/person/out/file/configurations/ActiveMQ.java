package de.synoa.getting.started.person.out.file.configurations;

import de.synoa.getting.started.person.out.file.App;
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

    public static final String QUEUE_PERSONS = "activemq:de.synoa.getting.started.helloworld.persons";

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

}
