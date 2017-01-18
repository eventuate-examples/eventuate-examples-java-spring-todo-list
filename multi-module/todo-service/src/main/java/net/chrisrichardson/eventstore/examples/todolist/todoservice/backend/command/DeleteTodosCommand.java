package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.command;

import java.util.List;


public class DeleteTodosCommand implements TodoCommand {
    private List<String> ids;

    public DeleteTodosCommand() {
    }

    public DeleteTodosCommand(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getIds() {
        return ids;
    }
}
