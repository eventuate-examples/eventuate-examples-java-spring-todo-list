package net.chrisrichardson.eventstore.examples.todolist.queryside.main;

import net.chrisrichardson.eventstore.examples.todolist.queryside.TodoQuerysideServiceConfiguration;
import org.springframework.boot.SpringApplication;


public class TodoQuerysideService {

    public static void main(String[] args) {
        SpringApplication.run(TodoQuerysideServiceConfiguration.class, args);
    }

}