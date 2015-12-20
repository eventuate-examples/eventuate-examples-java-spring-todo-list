# Todo List example application

The Todo List application is the hello world application for the [Eventuate&trade; Platform](http://eventuate.io).
It illustrates how you can use the platform to write an application that uses Event Sourcing and Command Query Responsibility Segregation (CQRS).
The Todo List application lets users maintain a todo list.

The Todo List application is a Java and Spring Boot application built using Eventuate&trade;'s Event Sourcing based programming model.
Todos are implemented by an Event Sourcing-based `TodoAggregate`.
The aggregate's events are persisted in the Eventuate event store.
The application also maintains a materialized view of the data in MySQL.

Don't forget to take a look at the other [Eventuate example applications](http://eventuate.io/exampleapps.html).

# Architecture

The following diagram shows the Todo List application architecture:

<img class="img-responsive" src="http://eventuate.io/demos/eventuate-todo-architecture.png">

The application consists of the following:

* Todo List Server - a Java and Spring Boot-based server-side application that has a HATEAOS-style REST API for creating, updating and querying todo list items.
* MySQL database - stores a materialized/denormalized view of todo list items.

Note: for simplicity, the Todo List service is deployed as a monolithic server but as you will see below, it consists of several independent modules.

# Todo List Server design

The Todo List server has a Spring MVC-based REST API for creating, updating and querying todo list items.
The Todo List server is written using the [Eventuate Client Framework for Java](http://eventuate.io/docs/java/eventuate-client-framework-for-java.html), which provides an event sourcing based programming model.
The server persists todo list items as events in the [Eventuate event store](http://eventuate.io/howeventuateworks.html).
The Todo List server also maintains a materialized view of the todo list in MySQL.

The following diagram shows the design of the server:

<img class="img-responsive" src="http://eventuate.io/demos/eventuate-todo-server.png">

The application is structured using the Command Query Responsibility Segregation (CQRS) pattern.
It consists of the following modules:

*  Command-side module - it handles requests to create and update (e.g. HTTP POST, PUT and DELETE requests) todo list items.
The business logic consists of event sourcing based `Todo` aggregates.

* Query-side module - it handles query requests (ie. HTTP GET requests) by querying a MySQL materialized view that it maintains.
It has an event handler that subscribes to Todo events and updates MySQL.

# Signing up for Eventuate

To run the Todo List application you need credentials for the Eventuate platform.
You can get them by [signing up here](https://signup.eventuate.io/).

# Running MySQL

In order to run the tests and to run the application you need a MySQL database.
The easiest way to run MySQL is with docker-compose:

```
docker-compose up -d mysql
```

# Building the application

Before building and/or running application, you must set an environment variable that tells the application how to connect to MySQL:

```
export SPRING_DATASOURCE_URL=jdbc:mysql://<DOCKER_IP_ADDRESS>:3307/es-test
```

You can then build the application using this Gradle command:

```
./gradlew clean build
```

Note: to use Gradle you just need to have the JDK in your path. You do not need to install it.

Note: that the the end-to-end tests in the `e2etest` module will fail because the service is not running.


# Running the service

Now that you built the application you can run the application using these commands:

```
docker-compose up -d
```

# Running the end to end tests

Now that the service is running you can run the end-to-end tests:

```
./gradlew :e2etest:cleanTest :e2etest:test
```

Note: the environment variable `DOCKER_HOST_IP` must be set to the hostname/IP address of where the service is running, e.g:

```
export DOCKER_HOST_IP=$(docker-machine ip default)
```

# Got questions?

Don't hesitate to create an issue or [contact us](http://eventuate.io/contact.html).
