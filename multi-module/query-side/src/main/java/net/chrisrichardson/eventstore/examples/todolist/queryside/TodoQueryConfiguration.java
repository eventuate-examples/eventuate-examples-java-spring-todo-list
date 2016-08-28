package net.chrisrichardson.eventstore.examples.todolist.queryside;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@EnableAutoConfiguration
@ComponentScan("net.chrisrichardson.eventstore.examples.todolist")
@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableEventHandlers
public class TodoQueryConfiguration {

    @Bean
    public TodoQueryWorkflow todoQueryWorkflow(TodoQueryServiceImpl queryService) {
        return new TodoQueryWorkflow(queryService);
    }

    @Bean
    public TodoQueryServiceImpl queryService(TodoRepository repository) {
        return new TodoQueryServiceImpl(repository);
    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}


