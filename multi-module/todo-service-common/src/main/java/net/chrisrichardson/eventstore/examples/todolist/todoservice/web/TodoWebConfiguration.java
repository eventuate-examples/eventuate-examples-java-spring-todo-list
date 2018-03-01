package net.chrisrichardson.eventstore.examples.todolist.todoservice.web;

import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.TodoBackendConfiguration;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({TodoBackendConfiguration.class})
@ComponentScan({ "net.chrisrichardson.eventstore.examples.todolist.common",
        "net.chrisrichardson.eventstore.examples.todolist.todoservice.web",
        "net.chrisrichardson.eventstore.examples.todolist.hateoas"})
public class TodoWebConfiguration extends WebMvcConfigurerAdapter {

  @Bean
  public ServletListenerRegistrationBean<RequestContextListener> httpRequestContextListener() {
    return new ServletListenerRegistrationBean<RequestContextListener>(new RequestContextListener());
  }

}
