package net.chrisrichardson.eventstore.examples.todolist.queryside;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commonswagger.CommonSwaggerConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({TodoQueryConfiguration.class, EventuateDriverConfiguration.class, CommonSwaggerConfiguration.class})
public class TodoQuerysideServiceConfiguration {

}
