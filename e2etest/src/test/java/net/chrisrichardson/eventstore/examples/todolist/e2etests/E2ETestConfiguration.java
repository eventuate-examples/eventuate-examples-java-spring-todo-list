package net.chrisrichardson.eventstore.examples.todolist.e2etests;

import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Configuration
@EnableAutoConfiguration(excludeName = {"org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration",
        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"})
public class E2ETestConfiguration extends BasicWebTestConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()))
                .errorHandler(new ResponseErrorHandler() {
                    @Override
                    public boolean hasError(ClientHttpResponse response) {
                        return false;
                    }

                    @Override
                    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {

                    }
                })
                .build();
    }

}
