package net.chrisrichardson.eventstore.examples.todolist.testutil;

import org.springframework.http.HttpStatusCode;


public class RestUtil {

    public static boolean isError(HttpStatusCode status) {
        return status.is4xxClientError() || status.is5xxServerError();
    }
}
