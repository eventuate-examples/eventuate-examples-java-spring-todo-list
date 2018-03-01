package net.chrisrichardson.eventstore.examples.todolist.queryside.backend;


import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoCreatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoUpdatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

@EventSubscriber(id = "todoQuerySideEventHandlers")
public class TodoViewEventSubscriber {

    private TodoUpdateServiceImpl todoQueryServiceImpl;

    public TodoViewEventSubscriber(TodoUpdateServiceImpl todoQueryServiceImpl) {
        this.todoQueryServiceImpl = todoQueryServiceImpl;
    }

    @EventHandlerMethod
    public void create(DispatchedEvent<TodoCreatedEvent> de) {
        Todo todo = new Todo(de.getEvent().getTodo());
        todo.setId(de.getEntityId());
        todoQueryServiceImpl.save(todo);
    }

    @EventHandlerMethod
    public void delete(DispatchedEvent<TodoDeletedEvent> de) {
        todoQueryServiceImpl.remove(de.getEntityId());
    }

    @EventHandlerMethod
    public void update(DispatchedEvent<TodoUpdatedEvent> de) {
        Todo todo = new Todo(de.getEvent().getTodo());
        todo.setId(de.getEntityId());
        todoQueryServiceImpl.save(todo);
    }
}
