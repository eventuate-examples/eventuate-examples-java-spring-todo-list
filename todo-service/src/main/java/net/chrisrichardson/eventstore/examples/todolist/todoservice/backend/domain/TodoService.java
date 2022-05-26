package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain;

import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.sync.AggregateRepository;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.command.*;

import java.util.List;


public class TodoService {

    private final AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository;
    private final AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository;


    public TodoService(AggregateRepository<TodoAggregate, TodoCommand> todoRepository, AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository) {
        this.aggregateRepository = todoRepository;
        this.bulkDeleteAggregateRepository = bulkDeleteAggregateRepository;
    }

    public EntityWithIdAndVersion<TodoAggregate> save(TodoInfo todo) {
        return aggregateRepository.save(new CreateTodoCommand(todo));
    }

    public EntityWithIdAndVersion<TodoAggregate> remove(String id) {
        return aggregateRepository.update(id, new DeleteTodoCommand());
    }

    public EntityWithIdAndVersion<TodoAggregate> update(String id, TodoInfo newTodo) {
        return aggregateRepository.update(id, new UpdateTodoCommand(id, newTodo));
    }

    public EntityWithIdAndVersion<TodoBulkDeleteAggregate> deleteAll(List<String> ids) {
        return bulkDeleteAggregateRepository.save(new DeleteTodosCommand(ids));
    }
}
