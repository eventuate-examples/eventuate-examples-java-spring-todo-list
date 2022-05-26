package net.chrisrichardson.eventstore.examples.todolist.hateoas;

import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

import java.util.List;
import java.util.Optional;


public interface TodoUpdateService {

    List<Todo> getAll();

    Optional<Todo> findById(String todoId);
}
