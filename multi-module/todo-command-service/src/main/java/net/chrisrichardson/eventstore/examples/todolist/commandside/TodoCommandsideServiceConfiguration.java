package net.chrisrichardson.eventstore.examples.todolist.commandside;

import net.chrisrichardson.eventstore.client.config.EventStoreHttpClientConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commandside.config.TodoCommandSideConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({TodoCommandSideConfiguration.class, EventStoreHttpClientConfiguration.class})
public class TodoCommandsideServiceConfiguration {

}
