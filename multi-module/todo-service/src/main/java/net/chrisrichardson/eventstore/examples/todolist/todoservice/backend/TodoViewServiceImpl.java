package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend;



import io.eventuate.CompletableFutureUtil;
import net.chrisrichardson.eventstore.examples.todolist.hateoas.TodoUpdateService;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


public class TodoViewServiceImpl implements TodoUpdateService {

    private TodoRepository repository;

    public TodoViewServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @Override
    public CompletableFuture<Todo> findById(String todoId) {
        return repository
                .findById(todoId)
                .map(CompletableFuture::completedFuture)
                .orElse(CompletableFutureUtil.failedFuture(new NoSuchElementException("No todo with given id found")));
    }

}
