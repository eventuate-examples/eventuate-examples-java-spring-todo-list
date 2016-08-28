package net.chrisrichardson.eventstore.examples.todolist.integration;

import io.eventuate.javaclient.spring.jdbc.EventuateJdbcEventStoreConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commandside.config.TodoCommandSideConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.queryside.TodoQueryConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({BasicWebTestConfiguration.class, EventuateJdbcEventStoreConfiguration.class, TodoCommandSideConfiguration.class, TodoQueryConfiguration.class})
public class RestAPITestConfiguration {
}
