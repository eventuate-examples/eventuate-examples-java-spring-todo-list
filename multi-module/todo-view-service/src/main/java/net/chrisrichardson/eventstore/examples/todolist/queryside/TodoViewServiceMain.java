package net.chrisrichardson.eventstore.examples.todolist.queryside;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.commonswagger.CommonSwaggerConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.queryside.backend.TodoViewBackendConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TodoViewBackendConfiguration.class,
        EventuateDriverConfiguration.class,
        CommonSwaggerConfiguration.class})
@EnableAutoConfiguration
public class TodoViewServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(TodoViewServiceMain.class, args);
  }

}