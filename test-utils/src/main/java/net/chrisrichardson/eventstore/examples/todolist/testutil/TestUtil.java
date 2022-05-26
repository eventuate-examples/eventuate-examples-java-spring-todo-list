package net.chrisrichardson.eventstore.examples.todolist.testutil;

import io.eventuate.util.test.async.Eventually;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.Assert.*;


public class TestUtil {

    public static <T> T awaitSuccessfulRequest(Supplier<ResponseEntity<T>> func, Predicate<T> predicate) {
        return awaitResponse(func, predicate, HttpStatus.OK);
    }

    private static <T> T awaitResponse(Supplier<ResponseEntity<T>> func, Predicate<T> predicate, HttpStatus expectedStatusCode) {
        return Eventually.eventuallyReturning(() -> {
                    ResponseEntity<T> re = func.get();
                    assertEquals(expectedStatusCode, re.getStatusCode());
                    assertTrue(predicate.test(re.getBody()));
                    return re.getBody();
                }
        );
    }

    public static <T> T awaitNotFoundResponse(Supplier<ResponseEntity<T>> func) {
        return awaitResponse(func, x -> true, HttpStatus.NOT_FOUND);
    }

    public static <T> T awaitSuccessfulRequest(Supplier<ResponseEntity<T>> func) {
        return awaitResponse(func, x -> true, HttpStatus.OK);
    }
}
