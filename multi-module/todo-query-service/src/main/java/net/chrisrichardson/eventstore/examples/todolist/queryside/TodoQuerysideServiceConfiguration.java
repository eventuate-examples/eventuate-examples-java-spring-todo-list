package net.chrisrichardson.eventstore.examples.todolist.queryside;

import io.eventuate.javaclient.spring.httpstomp.EventuateHttpStompClientConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commonswagger.CommonSwaggerConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({TodoQueryConfiguration.class, EventuateHttpStompClientConfiguration.class, CommonSwaggerConfiguration.class})
public class TodoQuerysideServiceConfiguration {

}
