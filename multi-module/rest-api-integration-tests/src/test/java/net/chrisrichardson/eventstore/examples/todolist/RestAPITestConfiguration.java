package net.chrisrichardson.eventstore.examples.todolist;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.queryside.backend.TodoViewBackendConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.TodoBackendConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({BasicWebTestConfiguration.class, EmbeddedTestAggregateStoreConfiguration.class, TodoBackendConfiguration.class, TodoViewBackendConfiguration.class})
public class RestAPITestConfiguration {
}
