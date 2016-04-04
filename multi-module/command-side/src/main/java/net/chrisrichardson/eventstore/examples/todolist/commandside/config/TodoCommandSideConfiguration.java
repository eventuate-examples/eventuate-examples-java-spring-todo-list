package net.chrisrichardson.eventstore.examples.todolist.commandside.config;

import net.chrisrichardson.eventstore.EventStore;
import net.chrisrichardson.eventstore.examples.todolist.commandside.TodoQueryServiceImpl;
import net.chrisrichardson.eventstore.examples.todolist.commandside.command.TodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.commandside.domain.TodoAggregate;
import net.chrisrichardson.eventstore.examples.todolist.commandside.domain.TodoBulkDeleteAggregate;
import net.chrisrichardson.eventstore.examples.todolist.commandside.domain.TodoCommandWorkflow;
import net.chrisrichardson.eventstore.examples.todolist.commandside.domain.TodoService;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import net.chrisrichardson.eventstore.javaapi.consumer.EnableJavaEventHandlers;
import net.chrisrichardson.eventstore.repository.AggregateRepository;
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
@Import({EventStoreSubscriptionsConfiguration.class, TodoWebCommandSideConfiguration.class})
@EnableAutoConfiguration
@ComponentScan("net.chrisrichardson.eventstore.examples.todolist")
@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableJavaEventHandlers
public class TodoCommandSideConfiguration {

    @Bean
    public TodoCommandWorkflow todoCommandWorkflow() {
        return new TodoCommandWorkflow();
    }

    @Bean
    public AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository(EventStore eventStore) {
        return new AggregateRepository<>(TodoAggregate.class, eventStore);
    }

    @Bean
    public AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository(EventStore eventStore) {
        return new AggregateRepository<>(TodoBulkDeleteAggregate.class, eventStore);
    }

    @Bean
    public TodoService updateService(AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository, AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository) {
        return new TodoService(aggregateRepository, bulkDeleteAggregateRepository);
    }

    @Bean
    public TodoQueryServiceImpl commandService(TodoRepository repository) {
        return new TodoQueryServiceImpl(repository);
    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}


