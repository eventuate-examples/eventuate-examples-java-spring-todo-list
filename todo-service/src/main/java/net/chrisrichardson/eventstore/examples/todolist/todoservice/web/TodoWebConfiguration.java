package net.chrisrichardson.eventstore.examples.todolist.todoservice.web;

import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.TodoBackendConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({TodoBackendConfiguration.class})
@ComponentScan({ "net.chrisrichardson.eventstore.examples.todolist.common",
        "net.chrisrichardson.eventstore.examples.todolist.todoservice.web",
        "net.chrisrichardson.eventstore.examples.todolist.hateoas"})
public class TodoWebConfiguration extends WebMvcConfigurerAdapter {
}
