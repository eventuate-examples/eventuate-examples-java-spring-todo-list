package net.chrisrichardson.eventstore.examples.todolist.commonswagger;

import io.eventuate.util.spring.swagger.EventuateSwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonSwaggerConfiguration {

  @Bean
  public EventuateSwaggerConfig eventuateSwaggerConfig() {
    return () -> "net.chrisrichardson.eventstore.examples.todolist.web";
  }
}
