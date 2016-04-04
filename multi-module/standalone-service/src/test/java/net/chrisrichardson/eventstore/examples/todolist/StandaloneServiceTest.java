package net.chrisrichardson.eventstore.examples.todolist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;


@SpringApplicationConfiguration(classes = {StandaloneServiceTestConfiguration.class})
@WebAppConfiguration
public class StandaloneServiceTest extends AbstractTodoRestAPITest {
    @Value("${local.server.port}")
    private int port;

    @Override
    protected int getCommandsidePort() {
        return port;
    }

    @Override
    protected String getCommandsideHost() {
        return "localhost";
    }

    @Override
    protected int getQuerysidePort() {
        return port;
    }

    @Override
    protected String getQuerysideHost() {
        return "localhost";
    }
}
