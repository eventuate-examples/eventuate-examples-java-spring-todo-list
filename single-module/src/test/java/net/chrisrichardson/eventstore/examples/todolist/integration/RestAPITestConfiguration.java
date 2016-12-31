package net.chrisrichardson.eventstore.examples.todolist.integration;

import io.eventuate.javaclient.spring.jdbc.EmbeddedTestAggregateStoreConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.backend.TodoBackendConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({BasicWebTestConfiguration.class, EmbeddedTestAggregateStoreConfiguration.class, TodoBackendConfiguration.class})
public class RestAPITestConfiguration {
}
