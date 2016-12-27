package net.chrisrichardson.eventstore.examples.todolist.queryside.web;

import net.chrisrichardson.eventstore.examples.todolist.queryside.backend.TodoViewBackendConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({TodoViewBackendConfiguration.class})
@ComponentScan({"net.chrisrichardson.eventstore.examples.todolist.common",
        "net.chrisrichardson.eventstore.examples.todolist.hateoas",
        "net.chrisrichardson.eventstore.examples.todolist.queryside.web"})
public class TodoViewWebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> httpRequestContextListener() {
        return new ServletListenerRegistrationBean<RequestContextListener>(new RequestContextListener());
    }

}
