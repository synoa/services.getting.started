package de.synoa.getting.started.person.out.file.aggregate;

import de.synoa.getting.started.person.out.file.model.Person;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;

public class PersonListAggregationStrategy extends AbstractListAggregationStrategy {

    @Override
    public Object getValue(Exchange exchange) {
        return exchange.getIn().getBody(Person.class);
    }
}
