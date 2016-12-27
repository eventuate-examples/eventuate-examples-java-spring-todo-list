package net.chrisrichardson.eventstore.examples.todolist.integrationtests;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.queryside.web.TodoViewWebConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.web.TodoWebConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({BasicWebTestConfiguration.class,
        EmbeddedTestAggregateStoreConfiguration.class,
        TodoWebConfiguration.class,
        TodoViewWebConfiguration.class})
public class RestAPITestConfiguration {
}
