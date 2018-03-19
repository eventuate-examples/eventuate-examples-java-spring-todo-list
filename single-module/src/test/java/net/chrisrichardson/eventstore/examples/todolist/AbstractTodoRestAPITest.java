package net.chrisrichardson.eventstore.examples.todolist;


import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static net.chrisrichardson.eventstore.examples.todolist.testutil.TestUtil.awaitNotFoundResponse;
import static net.chrisrichardson.eventstore.examples.todolist.testutil.TestUtil.awaitSuccessfulRequest;


@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractTodoRestAPITest {

    protected int port;

    private String baseUrl(String path) {
        return "http://" + getHost() + ":" + getPort() + "/" + path;
    }

    @Autowired
    private RestTemplate restTemplate;


    private String todoUrl(String todoId) {
        return baseUrl("todos/" + todoId);
    }

    private String todoUrl() {
        return baseUrl("todos");
    }


    private TodoWithUrl awaitCreationInView(String todoId) {
        return awaitSuccessfulRequest(() -> getTodo(todoId));
    }

    private ResponseEntity<TodoWithUrl> createTodo(TodoInfo todoToSave) {
        ResponseEntity<TodoWithUrl> postResponse = restTemplate.postForEntity(todoUrl(), todoToSave, TodoWithUrl.class);
        Assert.assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        return postResponse;
    }


    private ResponseEntity<TodoWithUrl> getTodo(String todoId) {
        return restTemplate.getForEntity(todoUrl(todoId), TodoWithUrl.class);
    }

    private ResponseEntity<TodoWithUrl[]> getTodos() {
        return restTemplate.getForEntity(todoUrl(), TodoWithUrl[].class);
    }

    private void assertTodoEquals(TodoWithUrl expectedTodo, TodoWithUrl todo) {
        Assert.assertEquals(expectedTodo.getTitle(), todo.getTitle());
        Assert.assertEquals(expectedTodo.getOrder(), todo.getOrder());
        Assert.assertEquals(expectedTodo.isCompleted(), todo.isCompleted());
        Assert.assertEquals(expectedTodo.getUrl(), todo.getUrl());
    }

    private void assertTodoContains(TodoWithUrl expectedTodo, List<TodoWithUrl> todoList) {
        Assert.assertTrue(todoList.contains(expectedTodo));
    }

    private TodoWithUrl makeExpectedTodo(String todoId, TodoInfo todo) {
        TodoWithUrl todoWithUrl = new TodoWithUrl();
        todoWithUrl.setCompleted(todo.isCompleted());
        todoWithUrl.setOrder(todo.getOrder());
        todoWithUrl.setTitle(todo.getTitle());
        todoWithUrl.setId(todoId);
        todoWithUrl.setUrl(todoUrl(todoId));
        return todoWithUrl;
    }

    private ResponseEntity<TodoWithUrl> updateTodo(String todoId, TodoInfo patch) {
        ResponseEntity<TodoWithUrl> patchResult = restTemplate.exchange(todoUrl(todoId), HttpMethod.PATCH, new HttpEntity<>(patch),
                TodoWithUrl.class);
        Assert.assertEquals(HttpStatus.OK, patchResult.getStatusCode());
        return patchResult;
    }

    private TodoWithUrl createAndWaitForView(TodoInfo todoToSave) {
        ResponseEntity<TodoWithUrl> postResponse = createTodo(todoToSave);

        String todoId = postResponse.getBody().getId();

        return awaitCreationInView(todoId);
    }

    @Test
    public void shouldSetCORSHeaders() {
        ResponseEntity<TodoWithUrl[]> responseEntity = getTodos();

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Allow-Origin").isEmpty());
        Assert.assertEquals("*", responseEntity.getHeaders().get("Access-Control-Allow-Origin").get(0));
        Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Allow-Methods").isEmpty());
        Assert.assertEquals("POST, GET, OPTIONS, DELETE, PATCH", responseEntity.getHeaders().get("Access-Control-Allow-Methods").get(0));
        Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Max-Age").isEmpty());
        Assert.assertEquals("3600", responseEntity.getHeaders().get("Access-Control-Max-Age").get(0));
        Assert.assertFalse(responseEntity.getHeaders().get("Access-Control-Allow-Headers").isEmpty());
        Assert.assertEquals("x-requested-with, origin, content-type, accept", responseEntity.getHeaders().get("Access-Control-Allow-Headers").get(0));
    }

    @Test
    public void shouldShowAllTodos() {
        TodoInfo todoToSave = new TodoInfo("1st todo");

        assertTodoContains(createAndWaitForView(todoToSave),
                Arrays.asList(getTodos().getBody())
        );
    }

    @Test
    public void shouldDeleteAllTodos() throws InterruptedException {
        TodoInfo todoToSave = new TodoInfo("a todo");
        createAndWaitForView(todoToSave);

        restTemplate.delete(todoUrl());

        awaitSuccessfulRequest(
                this::getTodos,
                re -> re.length == 0
        );

    }


    @Test
    public void shouldDeleteSingleTodo() throws InterruptedException {
        TodoInfo todoToSave = new TodoInfo("a todo");
        TodoWithUrl todo = createAndWaitForView(todoToSave);

        restTemplate.delete(todoUrl(todo.getId()));

        awaitNotFoundResponse(idx -> getTodo(todo.getId()));
    }

    @Test
    public void shouldCreateNewTodo() throws InterruptedException {
        TodoInfo todoToSave = new TodoInfo("walk the dog");
        TodoWithUrl todoView = createAndWaitForView(todoToSave);

        TodoWithUrl expectedTodo = makeExpectedTodo(todoView.getId(), todoToSave);

        assertTodoEquals(expectedTodo, todoView);
    }

    @Test
    public void shouldUpdateTodo() throws InterruptedException {

        TodoInfo todoToSave = new TodoInfo("todo 1");
        String todoId = createAndWaitForView(todoToSave).getId();

        TodoInfo todoWithChanges = new TodoInfo();
        todoWithChanges.setTitle("todo 2");
        todoWithChanges.setCompleted(true);
        todoWithChanges.setOrder(42);

        ResponseEntity<TodoWithUrl> patchResult = updateTodo(todoId, todoWithChanges);

        TodoWithUrl expectedTodo = makeExpectedTodo(todoId, todoWithChanges);

        TodoWithUrl updatedTodo = patchResult.getBody();
        assertTodoEquals(expectedTodo, updatedTodo);

        TodoWithUrl updatedTodoInView = awaitSuccessfulRequest(
                () -> getTodo(todoId),
                re -> re.getTitle().equals(todoWithChanges.getTitle())
        );

        assertTodoEquals(expectedTodo, updatedTodoInView);

    }


    protected abstract int getPort();

    protected abstract String getHost();
}

