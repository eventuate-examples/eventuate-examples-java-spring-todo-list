package net.chrisrichardson.eventstore.examples.todolist.commandside;



import io.eventuate.CompletableFutureUtil;
import net.chrisrichardson.eventstore.examples.todolist.TodoQueryService;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


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
    public CompletableFuture<Todo> findById(String todoId) {
        Todo res = repository.findOne(todoId);
        if (res != null) {
            return CompletableFuture.completedFuture(res);
        }
        return CompletableFutureUtil.failedFuture(new EntityNotFoundException("No todo found for given id"));
    }

}
