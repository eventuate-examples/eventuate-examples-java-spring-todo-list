package net.chrisrichardson.eventstore.examples.todolist.backend.command;


import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;

public class CreateTodoCommand implements TodoCommand {

    private TodoInfo todo;

    public CreateTodoCommand(TodoInfo todo) {
        this.todo = todo;
    }

    public TodoInfo getTodo() {
        return todo;
    }
}
