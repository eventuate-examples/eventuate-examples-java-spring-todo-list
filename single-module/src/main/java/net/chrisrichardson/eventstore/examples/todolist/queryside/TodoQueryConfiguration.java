package net.chrisrichardson.eventstore.examples.todolist.queryside;

import net.chrisrichardson.eventstore.javaapi.consumer.EnableJavaEventHandlers;
import net.chrisrichardson.eventstore.subscriptions.config.EventStoreSubscriptionsConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@Import({EventStoreSubscriptionsConfiguration.class})
@EnableAutoConfiguration
@ComponentScan({"net.chrisrichardson.eventstore.examples.todolist.queryside","net.chrisrichardson.eventstore.examples.todolist.common"})
@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableJavaEventHandlers
public class TodoQueryConfiguration {

    @Bean
    public TodoQueryWorkflow todoQueryWorkflow(TodoQueryService queryService) {
        return new TodoQueryWorkflow(queryService);
    }

    @Bean
    public TodoQueryService queryService(TodoRepository repository) {
        return new TodoQueryService(repository);
    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}


