package net.chrisrichardson.eventstore.examples.todolist.backend;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, String> {

}
