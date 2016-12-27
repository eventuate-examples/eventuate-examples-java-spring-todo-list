package net.chrisrichardson.eventstore.examples.todolist;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.backend.TodoBackendConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commonswagger.CommonSwaggerConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({TodoBackendConfiguration.class, EventuateDriverConfiguration.class, CommonSwaggerConfiguration.class})
public class TodoStandaloneServiceConfiguration {

}
