package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend;


import net.chrisrichardson.eventstore.examples.todolist.TodoRepository;
import net.chrisrichardson.eventstore.examples.todolist.hateoas.TodoUpdateService;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;

import java.util.List;
import java.util.Optional;


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
    public Optional<Todo> findById(String todoId) {
        return repository.findById(todoId);
    }

}
