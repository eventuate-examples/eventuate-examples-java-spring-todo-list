package net.chrisrichardson.eventstore.examples.todolist.queryside;

import io.eventuate.javaclient.spring.httpstomp.EventuateHttpStompClientConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({TodoQueryConfiguration.class, EventuateHttpStompClientConfiguration.class})
public class TodoQuerysideServiceConfiguration {

}
