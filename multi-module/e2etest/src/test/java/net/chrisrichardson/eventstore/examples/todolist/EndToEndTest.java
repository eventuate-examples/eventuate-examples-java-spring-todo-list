package net.chrisrichardson.eventstore.examples.todolist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;


@SpringApplicationConfiguration(classes = {E2ETestConfiguration.class})
@IntegrationTest
public class EndToEndTest extends AbstractTodoRestAPITest {

    @Value("#{systemEnvironment['DOCKER_HOST_IP']}")
    private String hostName;

    @Override
    protected int getCommandsidePort() {
        return 8081;
    }

    @Override
    protected String getCommandsideHost() {
        return hostName;
    }

    @Override
    protected int getQuerysidePort() {
        return 8082;
    }

    @Override
    protected String getQuerysideHost() {
        return hostName;
    }
}
