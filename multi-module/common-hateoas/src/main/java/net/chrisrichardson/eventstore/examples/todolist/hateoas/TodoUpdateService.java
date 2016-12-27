package net.chrisrichardson.eventstore.examples.todolist.hateoas;

import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by popikyardo on 23.03.16.
 */
public interface TodoUpdateService {

    List<Todo> getAll();

    CompletableFuture<Todo> findById(String todoId);
}
