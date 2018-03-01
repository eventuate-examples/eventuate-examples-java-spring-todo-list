package net.chrisrichardson.eventstore.examples.todolist.queryside.web;

import net.chrisrichardson.eventstore.examples.todolist.hateoas.TodoHateoasController;
import net.chrisrichardson.eventstore.examples.todolist.common.controller.BaseController;
import net.chrisrichardson.eventstore.examples.todolist.common.model.ResourceWithUrl;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import net.chrisrichardson.eventstore.examples.todolist.queryside.backend.TodoUpdateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@RequestMapping(value = "/todos")
public class TodoViewController extends BaseController {

    @Autowired
    private TodoUpdateServiceImpl queryService;

    @RequestMapping(method = GET)
    public HttpEntity<Collection<ResourceWithUrl>> listAll() {
        List<ResourceWithUrl> resourceWithUrls = queryService.getAll().stream().map(this::toResource).collect(Collectors.toList());
        return new ResponseEntity<>(resourceWithUrls, OK);
    }

    public ResourceWithUrl toResource(Todo todo) {
        ResourceWithUrl<Todo> result = new ResourceWithUrl<>(todo);
        if (todo != null) {
            result.setUrl(linkTo(methodOn(TodoHateoasController.class).getTodo(todo.getId())).withSelfRel().getHref());
        }
        return result;
    }
}
