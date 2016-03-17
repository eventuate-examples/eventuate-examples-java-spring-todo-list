package net.chrisrichardson.eventstore.examples.todolist;

import net.chrisrichardson.eventstore.examples.todolist.commandside.config.TodoCommandSideConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.queryside.TodoQueryConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({BasicWebTestConfiguration.class, JdbcEventStoreConfiguration.class, TodoCommandSideConfiguration.class, TodoQueryConfiguration.class})
public class RestAPITestConfiguration {
}
