package net.chrisrichardson.eventstore.examples.todolist;

import net.chrisrichardson.eventstore.examples.todolist.testutil.BasicWebTestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({TodoStandaloneServiceConfiguration.class, BasicWebTestConfiguration.class})
public class StandaloneServiceTestConfiguration {
}
