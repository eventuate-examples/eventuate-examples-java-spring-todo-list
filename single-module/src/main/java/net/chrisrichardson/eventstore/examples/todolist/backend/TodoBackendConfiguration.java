package net.chrisrichardson.eventstore.examples.todolist.backend;

import io.eventuate.AggregateRepository;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.TodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.domain.TodoAggregate;
import net.chrisrichardson.eventstore.examples.todolist.backend.domain.TodoBulkDeleteAggregate;
import net.chrisrichardson.eventstore.examples.todolist.backend.domain.TodoCommandWorkflow;
import net.chrisrichardson.eventstore.examples.todolist.backend.domain.TodoService;
import net.chrisrichardson.eventstore.examples.todolist.web.TodoWebConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@Import({TodoWebConfiguration.class})
@EnableAutoConfiguration
@ComponentScan({"net.chrisrichardson.eventstore.examples.todolist.backend",
                "net.chrisrichardson.eventstore.examples.todolist.common"})
@EntityScan("net.chrisrichardson.eventstore.examples.todolist")
@EnableJpaRepositories("net.chrisrichardson.eventstore.examples.todolist")
@EnableEventHandlers
public class TodoBackendConfiguration {

    @Bean
    public TodoCommandWorkflow todoCommandWorkflow() {
        return new TodoCommandWorkflow();
    }

    @Bean
    public AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository(EventuateAggregateStore eventStore) {
        return new AggregateRepository<>(TodoAggregate.class, eventStore);
    }

    @Bean
    public AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository(EventuateAggregateStore eventStore) {
        return new AggregateRepository<>(TodoBulkDeleteAggregate.class, eventStore);
    }

    @Bean
    public TodoService updateService(AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository, AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository) {
        return new TodoService(aggregateRepository, bulkDeleteAggregateRepository);
    }

    @Bean
    public TodoQueryWorkflow todoQueryWorkflow(TodoQueryService queryService) {
        return new TodoQueryWorkflow(queryService);
    }

    @Bean
    public TodoQueryService queryService(TodoRepository repository) {
        return new TodoQueryService(repository);
    }
}


