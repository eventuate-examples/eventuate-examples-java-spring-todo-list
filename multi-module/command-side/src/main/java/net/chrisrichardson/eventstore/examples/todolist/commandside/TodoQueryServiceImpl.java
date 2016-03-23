package net.chrisrichardson.eventstore.examples.todolist.commandside;



import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.todolist.TodoQueryService;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import rx.Observable;

import java.util.List;
import java.util.Optional;


public class TodoQueryServiceImpl implements TodoQueryService {

    private TodoRepository repository;

    public TodoQueryServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Todo> findById(String id) {
        return Optional.of(repository.findOne(id));
    }

    @Override
    public Observable<Todo> findById(EntityIdentifier todoId) {
        Todo res = repository.findOne(todoId.getId());
        if (res != null) {
            return Observable.just(res);
        }
        return Observable.empty();
    }

}
