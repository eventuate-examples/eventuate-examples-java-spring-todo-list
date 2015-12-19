package net.chrisrichardson.eventstore.examples.todolist.queryside;


import net.chrisrichardson.eventstore.EntityIdentifier;
import rx.Observable;

import java.util.List;
import java.util.Optional;


public class TodoQueryService {

    private TodoRepository repository;

    public TodoQueryService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public void remove(String id) {
        repository.delete(id);
    }

    public void removeAll() {
        repository.deleteAll();
    }

    public Optional<Todo> findById(String id) {
        return Optional.of(repository.findOne(id));
    }

    public Observable<Todo> findById(EntityIdentifier todoId) {
        Todo res = repository.findOne(todoId.getId());
        if (res != null) {
            return Observable.just(res);
        }
        return Observable.empty();
    }


}
