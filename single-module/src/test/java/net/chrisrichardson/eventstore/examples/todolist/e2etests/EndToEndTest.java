package net.chrisrichardson.eventstore.examples.todolist.e2etests;

import net.chrisrichardson.eventstore.examples.todolist.AbstractTodoRestAPITest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = E2ETestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EndToEndTest extends AbstractTodoRestAPITest {

    @Value("#{systemEnvironment['DOCKER_HOST_IP']}")
    private String hostName;

    @Override
    protected int getPort() {
        return 8080;
    }

    @Override
    protected String getHost() {
        return hostName;
    }
}
