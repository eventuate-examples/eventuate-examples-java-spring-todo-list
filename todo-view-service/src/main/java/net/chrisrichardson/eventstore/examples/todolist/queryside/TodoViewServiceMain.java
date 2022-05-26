package net.chrisrichardson.eventstore.examples.todolist.queryside;

import io.eventuate.local.java.spring.javaclient.driver.EventuateDriverConfiguration;
import net.chrisrichardson.eventstore.examples.todolist.queryside.web.TodoViewWebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TodoViewWebConfiguration.class,
        EventuateDriverConfiguration.class})
@EnableAutoConfiguration
public class TodoViewServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(TodoViewServiceMain.class, args);
  }

}