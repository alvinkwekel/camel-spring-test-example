package corp.ci.mdw;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.CamelTestContextBootstrapper;
import org.apache.camel.util.ObjectHelper;
import org.apache.camel.util.ServiceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@BootstrapWith(CamelTestContextBootstrapper.class)
@ContextConfiguration("/META-INF/spring/camel-context.xml")
public class ExchangeCountTest {

    @Autowired CamelContext camelContext;

    @Test
    public void test1() throws InterruptedException {
        Notifier eventNotifier = new Notifier();
        try {
           ServiceHelper.startService(eventNotifier);
        } catch (Exception e) {
           throw ObjectHelper.wrapRuntimeCamelException(e);
        }
        camelContext.getManagementStrategy().addEventNotifier(eventNotifier);
        NotifyBuilder notify = new NotifyBuilder(camelContext).fromRoute("routeId1").whenDone(2).create();
        assertTrue(notify.matches(10, TimeUnit.SECONDS)); 
    }
}
