package demo.jaxrs.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import demo.jaxrs.resource.TaskResource;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(TaskResource.class);
        // You can register more filters, exception mappers, etc.
    }
}
