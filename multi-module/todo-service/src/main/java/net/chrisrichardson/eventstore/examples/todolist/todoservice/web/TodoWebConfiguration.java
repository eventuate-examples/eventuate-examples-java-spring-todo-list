package net.chrisrichardson.eventstore.examples.todolist.todoservice.web;

import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class TodoWebConfiguration extends WebMvcConfigurerAdapter {

  @Bean
  public ServletListenerRegistrationBean<RequestContextListener> httpRequestContextListener() {
    return new ServletListenerRegistrationBean<RequestContextListener>(new RequestContextListener());
  }

}
