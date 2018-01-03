package corp.ci.mdw;

import java.util.EventObject;

import org.apache.camel.Exchange;
import org.apache.camel.management.event.AbstractExchangeEvent;
import org.apache.camel.management.event.ExchangeCompletedEvent;
import org.apache.camel.support.EventNotifierSupport;

public class Notifier extends EventNotifierSupport {

    @Override
    public void notify(EventObject event) throws Exception {
        AbstractExchangeEvent exchangeEvent = (AbstractExchangeEvent) event;
        Exchange exchange = exchangeEvent.getExchange();
        System.out.println(exchange.getExchangeId() + exchange.getFromEndpoint());
    }

    @Override
    public boolean isEnabled(EventObject event) {
        return event instanceof ExchangeCompletedEvent;
    }

}
