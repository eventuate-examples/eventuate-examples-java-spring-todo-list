package net.chrisrichardson.eventstore.examples.todolist.integrationtests;

import net.chrisrichardson.eventstore.examples.todolist.AbstractTodoRestAPITest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;


@SpringApplicationConfiguration(classes = {RestAPITestConfiguration.class})
@WebAppConfiguration
public class RestAPITest extends AbstractTodoRestAPITest {
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
