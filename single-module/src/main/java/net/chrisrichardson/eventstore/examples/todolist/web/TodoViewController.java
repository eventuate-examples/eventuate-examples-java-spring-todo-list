package net.chrisrichardson.eventstore.examples.todolist.web;

import net.chrisrichardson.eventstore.examples.todolist.common.controller.BaseController;
import net.chrisrichardson.eventstore.examples.todolist.model.ResourceWithUrl;
import net.chrisrichardson.eventstore.examples.todolist.backend.Todo;
import net.chrisrichardson.eventstore.examples.todolist.backend.TodoQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(value = "/todos")
public class TodoViewController extends BaseController {

    @Autowired
    private TodoQueryService queryService;

    @RequestMapping(method = GET)
    public HttpEntity<Collection<ResourceWithUrl>> listAll() {
        List<ResourceWithUrl> resourceWithUrls = queryService.getAll().stream().map(this::toResource).collect(Collectors.toList());
        return new ResponseEntity<>(resourceWithUrls, OK);
    }

    @RequestMapping(value = "/{todo-id}", method = GET)
    public CompletableFuture<ResourceWithUrl> getTodo(@PathVariable("todo-id") String id) {
        return queryService.findById(id).thenApply(this::toResource);
    }

    protected ResourceWithUrl toResource(Todo todo) {
        ResourceWithUrl<Todo> result = new ResourceWithUrl<>(todo);
        if (todo != null) {
            result.setUrl(linkTo(methodOn(TodoViewController.class).getTodo(todo.getId())).withSelfRel().getHref());
        }
        return result;
    }
}
