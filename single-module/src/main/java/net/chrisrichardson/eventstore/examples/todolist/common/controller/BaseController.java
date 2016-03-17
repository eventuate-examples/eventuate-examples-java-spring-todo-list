package net.chrisrichardson.eventstore.examples.todolist.common.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;


public class BaseController {

    protected <T> T withRequestAttributeContext(HttpServletRequest request, Callable<T> runnable) {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        try {
            return runnable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            RequestContextHolder.resetRequestAttributes();
        }
    }

}
