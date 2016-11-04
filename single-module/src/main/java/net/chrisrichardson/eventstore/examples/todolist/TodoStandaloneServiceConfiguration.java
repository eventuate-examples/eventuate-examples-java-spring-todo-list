package net.chrisrichardson.eventstore.examples.todolist;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commandside.config.TodoCommandSideConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commonswagger.CommonSwaggerConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({TodoCommandSideConfiguration.class, EventuateDriverConfiguration.class, CommonSwaggerConfiguration.class})
public class TodoStandaloneServiceConfiguration {

}
