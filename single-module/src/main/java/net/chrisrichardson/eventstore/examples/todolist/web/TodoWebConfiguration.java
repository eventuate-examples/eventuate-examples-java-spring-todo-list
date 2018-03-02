package net.chrisrichardson.eventstore.examples.todolist.web;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("net.chrisrichardson.eventstore.examples.todolist.web")
public class TodoWebConfiguration {

    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> httpRequestContextListener() {
        return new ServletListenerRegistrationBean<>(new RequestContextListener());
    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}
