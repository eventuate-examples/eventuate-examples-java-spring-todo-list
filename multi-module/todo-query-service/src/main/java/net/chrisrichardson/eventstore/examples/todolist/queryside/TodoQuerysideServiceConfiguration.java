package net.chrisrichardson.eventstore.examples.todolist.queryside;

import net.chrisrichardson.eventstore.client.config.EventStoreHttpClientConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({TodoQueryConfiguration.class, EventStoreHttpClientConfiguration.class})
public class TodoQuerysideServiceConfiguration {

}
