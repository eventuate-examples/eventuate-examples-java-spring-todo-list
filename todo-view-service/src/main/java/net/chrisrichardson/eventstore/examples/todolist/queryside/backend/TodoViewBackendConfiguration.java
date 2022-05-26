package net.chrisrichardson.eventstore.examples.todolist.queryside.backend;

import io.eventuate.javaclient.spring.EnableEventHandlers;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableEventHandlers
public class TodoViewBackendConfiguration {

    @Bean
    public TodoViewEventSubscriber todoViewEventSubscriber(TodoUpdateServiceImpl queryService) {
        return new TodoViewEventSubscriber(queryService);
    }

    @Bean
    public TodoUpdateServiceImpl queryService(TodoRepository repository) {
        return new TodoUpdateServiceImpl(repository);
    }
}


