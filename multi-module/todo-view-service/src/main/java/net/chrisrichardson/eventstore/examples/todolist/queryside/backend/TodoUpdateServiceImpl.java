package net.chrisrichardson.eventstore.examples.todolist.queryside.backend;


import io.eventuate.CompletableFutureUtil;
import net.chrisrichardson.eventstore.examples.todolist.hateoas.TodoUpdateService;
import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


public class TodoUpdateServiceImpl implements TodoUpdateService {

    private TodoRepository repository;

    public TodoUpdateServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public List<Todo> getAll() {
        return repository.findAll();
    }

    public void remove(String id) {
        repository.deleteById(id);
    }

    public void removeAll() {
        repository.deleteAll();
    }

    public CompletableFuture<Todo> findById(String todoId) {
        return repository
                .findById(todoId)
                .map(CompletableFuture::completedFuture)
                .orElse(CompletableFutureUtil.failedFuture(new NoSuchElementException("No todo with given id found")));
    }

}
