package net.chrisrichardson.eventstore.examples.todolist.testutil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


public class TestUtil {

    public static <T> T awaitSuccessfulRequest(Supplier<ResponseEntity<T>> func, Func1<T, Boolean> predicate) {
        try {
            return Observable.interval(400, TimeUnit.MILLISECONDS)
                    .take(50)
                    .map(x -> func.get())
                    .filter(re -> re.getStatusCode().equals(HttpStatus.OK) && re.getBody() != null && predicate.call(re.getBody()))
                    .toBlocking().first().getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseEntity awaitNotFoundResponse(Func1<Long, ResponseEntity> func) {
        try {
            return Observable.interval(400, TimeUnit.MILLISECONDS)
                    .take(50)
                    .map(func)
                    .filter(re -> re.getStatusCode().equals(HttpStatus.NOT_FOUND))
                    .toBlocking().first();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T awaitSuccessfulRequest(Supplier<ResponseEntity<T>> func) {
        try {
            return Observable.interval(400, TimeUnit.MILLISECONDS)
                    .take(50)
                    .map(x -> func.get())
                    .filter(re -> re.getStatusCode().equals(HttpStatus.OK) && re.getBody() != null)
                    .toBlocking().first().getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
