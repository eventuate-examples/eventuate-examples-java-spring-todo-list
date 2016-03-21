package net.chrisrichardson.eventstore.examples.todolist.queryside;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo, String> {

}
