package net.chrisrichardson.eventstore.examples.todolist.commandside.main;

import net.chrisrichardson.eventstore.examples.todolist.commandside.TodoCommandsideServiceConfiguration;
import org.springframework.boot.SpringApplication;


public class TodoCommandsideService {

    public static void main(String[] args) {
        SpringApplication.run(TodoCommandsideServiceConfiguration.class, args);
    }

}