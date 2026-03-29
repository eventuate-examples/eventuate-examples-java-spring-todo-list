package net.chrisrichardson.eventstore.examples.todolist.common.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ResourceWithUrl<T> {
    private String id;
    @JsonUnwrapped
    private T content;

    private String url;

    public ResourceWithUrl() {
    }

    public ResourceWithUrl(T content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
