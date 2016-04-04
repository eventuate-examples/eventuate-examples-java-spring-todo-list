package net.chrisrichardson.eventstore.examples.todolist;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import rx.Observable;

import java.util.List;
import java.util.Optional;

/**
 * Created by popikyardo on 23.03.16.
 */
public interface TodoQueryService {

    List<Todo> getAll();

    Optional<Todo> findById(String id);

    Observable<Todo> findById(EntityIdentifier todoId);
}
