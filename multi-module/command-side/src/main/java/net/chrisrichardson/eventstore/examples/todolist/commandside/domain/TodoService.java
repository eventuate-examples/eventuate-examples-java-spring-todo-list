package net.chrisrichardson.eventstore.examples.todolist.commandside.domain;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.todolist.commandside.command.*;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import net.chrisrichardson.eventstore.repository.AggregateRepository;
import rx.Observable;

import java.util.List;


public class TodoService {

    private final AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository;
    private final AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository;


    public TodoService(AggregateRepository<TodoAggregate, TodoCommand> todoRepository, AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository) {
        this.aggregateRepository = todoRepository;
        this.bulkDeleteAggregateRepository = bulkDeleteAggregateRepository;
    }

    public Observable<EntityWithIdAndVersion<TodoAggregate>> save(TodoInfo todo) {
        return aggregateRepository.save(new CreateTodoCommand(todo));
    }

    public Observable<EntityWithIdAndVersion<TodoAggregate>> remove(String id) {
        return aggregateRepository.update(new EntityIdentifier(id), new DeleteTodoCommand());
    }

    public Observable<EntityWithIdAndVersion<TodoAggregate>> update(String id, TodoInfo newTodo) {
        return aggregateRepository.update(new EntityIdentifier(id), new UpdateTodoCommand(id, newTodo));
    }

    public Observable<EntityWithIdAndVersion<TodoBulkDeleteAggregate>> deleteAll(List<String> ids) {
        return bulkDeleteAggregateRepository.save(new DeleteTodosCommand(ids));
    }
}
