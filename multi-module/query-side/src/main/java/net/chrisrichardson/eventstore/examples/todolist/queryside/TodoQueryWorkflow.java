package net.chrisrichardson.eventstore.examples.todolist.queryside;


import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoCreatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoUpdatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import net.chrisrichardson.eventstore.subscriptions.CompoundEventHandler;
import net.chrisrichardson.eventstore.subscriptions.DispatchedEvent;
import net.chrisrichardson.eventstore.subscriptions.EventHandlerMethod;
import net.chrisrichardson.eventstore.subscriptions.EventSubscriber;
import rx.Observable;

@EventSubscriber(id = "todoQuerySideEventHandlers")
public class TodoQueryWorkflow implements CompoundEventHandler {

    private TodoQueryServiceImpl todoQueryServiceImpl;

    public TodoQueryWorkflow(TodoQueryServiceImpl todoQueryServiceImpl) {
        this.todoQueryServiceImpl = todoQueryServiceImpl;
    }

    @EventHandlerMethod
    public Observable<Object> create(DispatchedEvent<TodoCreatedEvent> de) {
        Todo todo = new Todo(de.event().getTodo());
        todo.setId(de.getEntityIdentifier().getId());
        return Observable.just(todoQueryServiceImpl.save(todo));
    }

    @EventHandlerMethod
    public Observable<Object> delete(DispatchedEvent<TodoDeletedEvent> de) {
        todoQueryServiceImpl.remove(de.getEntityIdentifier().getId());
        return Observable.just(null);
    }

    @EventHandlerMethod
    public Observable<Object> update(DispatchedEvent<TodoUpdatedEvent> de) {
        Todo todo = new Todo(de.event().getTodo());
        todo.setId(de.getEntityIdentifier().getId());
        return Observable.just(todoQueryServiceImpl.save(todo));
    }
}
