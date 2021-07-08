package net.chrisrichardson.eventstore.examples.todolist.todoservice.web;

import net.chrisrichardson.eventstore.examples.todolist.hateoas.TodoHateoasController;
import net.chrisrichardson.eventstore.examples.todolist.common.controller.BaseController;
import net.chrisrichardson.eventstore.examples.todolist.common.model.ResourceWithUrl;
import net.chrisrichardson.eventstore.examples.todolist.model.Todo;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.TodoViewServiceImpl;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.web.bind.annotation.RequestMethod.*;


@RestController
@RequestMapping(value = "/todos")
public class TodoController extends BaseController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoViewServiceImpl todoViewService;

    @RequestMapping(method = POST)
    public CompletableFuture<ResourceWithUrl> saveTodo(@RequestBody TodoInfo todo, HttpServletRequest request) {
        Assert.notNull(todo.getTitle());
        return todoService.save(todo).thenApply(e -> withRequestAttributeContext(request, () -> toResource(e.getAggregate().getTodo(), e.getEntityId())));
    }

    @RequestMapping(value = "/{todo-id}", method = DELETE)
    public CompletableFuture<ResourceWithUrl> deleteOneTodo(@PathVariable("todo-id") String id, HttpServletRequest request) {
        return todoService.remove(id)
                .thenApply(e -> withRequestAttributeContext(request, () -> toResource(e.getAggregate().getTodo(), e.getEntityId())));
    }

    @RequestMapping(method = DELETE)
    public void deleteAllTodos() throws Exception {
        List<Todo> todosToDelete = todoViewService.getAll();
        if (todosToDelete.size() > 0) {
            todoService.deleteAll(todoViewService.getAll()
                    .stream()
                    .map(Todo::getId)
                    .collect(Collectors.toList()));
        }
    }

    @RequestMapping(value = "/{todo-id}", method = PATCH, headers = {"Content-type=application/json"})
    public CompletableFuture<ResourceWithUrl> updateTodo(@PathVariable("todo-id") String id, @RequestBody TodoInfo newTodo, HttpServletRequest request) {
        return todoService.update(id, newTodo).thenApply(e -> withRequestAttributeContext(request, () -> toResource(e.getAggregate().getTodo(), e.getEntityId()))
        );
    }

    protected ResourceWithUrl toResource(TodoInfo todo, String id) {
        ResourceWithUrl<TodoInfo> result = new ResourceWithUrl<>(todo);
        result.setId(id);
        result.setUrl(ControllerLinkBuilder.linkTo(methodOn(TodoHateoasController.class).getTodo(id)).withSelfRel().getHref());
        return result;
    }
}
