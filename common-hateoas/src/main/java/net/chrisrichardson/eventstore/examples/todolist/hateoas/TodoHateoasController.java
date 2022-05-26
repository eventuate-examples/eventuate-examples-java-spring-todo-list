package net.chrisrichardson.eventstore.examples.todolist.hateoas;

import net.chrisrichardson.eventstore.examples.todolist.common.controller.BaseController;
import net.chrisrichardson.eventstore.examples.todolist.common.model.ResourceWithUrl;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/todos")
public class TodoHateoasController extends BaseController {

    @Autowired
    private TodoUpdateService queryService;

    @RequestMapping(value = "/{todo-id}", method = GET)
    public ResponseEntity<ResourceWithUrl> getTodo(@PathVariable("todo-id") String id) {
        return queryService.findById(id)
                .map(todo -> ResponseEntity.ok(toResource(todo)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResourceWithUrl toResource(Todo todo) {
        ResourceWithUrl<Todo> result = new ResourceWithUrl<>(todo);
        if (todo != null) {
            result.setUrl(linkTo(methodOn(TodoHateoasController.class).getTodo(todo.getId())).withSelfRel().getHref());
        }
        return result;
    }
}
