package net.chrisrichardson.eventstore.examples.todolist.backend;


import io.eventuate.CompletableFutureUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


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

    public CompletableFuture<Todo> findById(String todoId) {
        Todo res = repository.findOne(todoId);
        if (res != null) {
            return CompletableFuture.completedFuture(res);
        }
        return CompletableFutureUtil.failedFuture(new NoSuchElementException("No todo with given id found"));
    }


}
