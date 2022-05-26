package net.chrisrichardson.eventstore.examples.todolist.model;


public class TodoInfo {
    private String title;
    private boolean completed;
    private int order;

    public TodoInfo() {
    }

    public TodoInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
