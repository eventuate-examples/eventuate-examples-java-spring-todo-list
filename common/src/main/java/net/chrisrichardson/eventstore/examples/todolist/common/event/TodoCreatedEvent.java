package net.chrisrichardson.eventstore.examples.todolist.common.event;

import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;

public class TodoCreatedEvent implements TodoEvent {

    TodoInfo todo;

    private TodoCreatedEvent() {
    }

    public TodoCreatedEvent(TodoInfo todo) {
        this.todo = todo;
    }

    public TodoInfo getTodo() {
        return todo;
    }

    public void setTodo(TodoInfo todo) {
        this.todo = todo;
    }
}