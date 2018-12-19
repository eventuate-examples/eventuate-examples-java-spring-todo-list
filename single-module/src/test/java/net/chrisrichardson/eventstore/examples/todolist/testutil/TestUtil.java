package net.chrisrichardson.eventstore.examples.todolist.testutil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rx.Observable;
import rx.functions.Func1;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


public class TestUtil {

    private static final int DEFAULT_INTERVAL_MS = Optional
            .ofNullable(System.getenv("TODO_LIST_DEFAULT_INTERVAL_MS"))
            .map(Integer::parseInt)
            .orElse(400);

    private static final int DEFAULT_ITERATIONS = Optional
            .ofNullable(System.getenv("TODO_LIST_DEFAULT_ITERATIONS"))
            .map(Integer::parseInt)
            .orElse(50);

    public static <T> T awaitSuccessfulRequest(Supplier<ResponseEntity<T>> func, Func1<T, Boolean> predicate) {
        try {
            return Observable.interval(DEFAULT_INTERVAL_MS, TimeUnit.MILLISECONDS)
                    .take(DEFAULT_ITERATIONS)
                    .map(x -> func.get())
                    .filter(re -> re.getStatusCode().equals(HttpStatus.OK) && re.getBody() != null && predicate.call(re.getBody()))
                    .toBlocking().first().getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseEntity awaitNotFoundResponse(Func1<Long, ResponseEntity> func) {
        try {
            return Observable.interval(DEFAULT_INTERVAL_MS, TimeUnit.MILLISECONDS)
                    .take(DEFAULT_ITERATIONS)
                    .map(func)
                    .filter(re -> re.getStatusCode().equals(HttpStatus.NOT_FOUND))
                    .toBlocking().first();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T awaitSuccessfulRequest(Supplier<ResponseEntity<T>> func) {
        try {
            return Observable.interval(DEFAULT_INTERVAL_MS, TimeUnit.MILLISECONDS)
                    .take(DEFAULT_ITERATIONS)
                    .map(x -> func.get())
                    .filter(re -> re.getStatusCode().equals(HttpStatus.OK) && re.getBody() != null)
                    .toBlocking().first().getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
