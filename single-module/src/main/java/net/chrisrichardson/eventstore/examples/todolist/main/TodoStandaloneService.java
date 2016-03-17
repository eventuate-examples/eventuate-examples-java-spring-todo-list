package net.chrisrichardson.eventstore.examples.todolist.main;

import net.chrisrichardson.eventstore.examples.todolist.TodoStandaloneServiceConfiguration;
import org.springframework.boot.SpringApplication;


public class TodoStandaloneService {

    public static void main(String[] args) {
        SpringApplication.run(TodoStandaloneServiceConfiguration.class, args);
    }

}