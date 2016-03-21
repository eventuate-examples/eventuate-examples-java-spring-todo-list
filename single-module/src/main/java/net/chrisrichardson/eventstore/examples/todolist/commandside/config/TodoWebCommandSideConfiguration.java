package net.chrisrichardson.eventstore.examples.todolist.commandside.config;

import net.chrisrichardson.eventstore.examples.todolist.common.util.ObservableReturnValueHandler;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan("net.chrisrichardson.eventstore.examples.todolist.web")
public class TodoWebCommandSideConfiguration extends WebMvcConfigurerAdapter {

    class FakeThing {
    }

    @Bean
    public FakeThing init(RequestMappingHandlerAdapter adapter) {
        // https://jira.spring.io/browse/SPR-13083
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(adapter.getReturnValueHandlers());
        handlers.add(0, new ObservableReturnValueHandler());
        adapter.setReturnValueHandlers(handlers);
        return new FakeThing();
    }

    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> httpRequestContextListener() {
        return new ServletListenerRegistrationBean<RequestContextListener>(new RequestContextListener());
    }

}
