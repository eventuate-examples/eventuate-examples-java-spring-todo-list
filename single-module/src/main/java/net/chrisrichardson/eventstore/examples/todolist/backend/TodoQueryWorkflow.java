package net.chrisrichardson.eventstore.examples.todolist.backend;


import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoCreatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoUpdatedEvent;

@EventSubscriber(id = "todoQuerySideEventHandlers")
public class TodoQueryWorkflow {

  private TodoQueryService todoQueryService;

  public TodoQueryWorkflow(TodoQueryService todoQueryService) {
    this.todoQueryService = todoQueryService;
  }

  @EventHandlerMethod
  public void create(DispatchedEvent<TodoCreatedEvent> de) {
    Todo todo = new Todo(de.getEvent().getTodo());
    todo.setId(de.getEntityId());

    todoQueryService.save(todo);
  }

  @EventHandlerMethod
  public void delete(DispatchedEvent<TodoDeletedEvent> de) {
    todoQueryService.remove(de.getEntityId());
  }

  @EventHandlerMethod
  public void update(DispatchedEvent<TodoUpdatedEvent> de) {
    Todo todo = new Todo(de.getEvent().getTodo());
    todo.setId(de.getEntityId());

    todoQueryService.save(todo);
  }
}
