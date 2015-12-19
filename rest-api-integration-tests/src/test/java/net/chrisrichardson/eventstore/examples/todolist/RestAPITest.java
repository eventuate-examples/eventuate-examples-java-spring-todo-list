package net.chrisrichardson.eventstore.examples.todolist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;


@SpringApplicationConfiguration(classes = {RestAPITestConfiguration.class})
@WebAppConfiguration
public class RestAPITest extends AbstractTodoRestAPITest {
    @Value("${local.server.port}")
    private int port;

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected String getHost() {
        return "localhost";
    }
}
