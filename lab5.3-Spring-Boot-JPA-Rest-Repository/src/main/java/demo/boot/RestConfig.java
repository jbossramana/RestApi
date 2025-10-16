package demo.boot;

import demo.boot.model.Task;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // 1. Base path
        config.setBasePath("/api");

        // 2. Expose IDs
        config.exposeIdsFor(Task.class);

        // 3. Paging defaults
        config.setDefaultPageSize(10);
        config.setMaxPageSize(100);

        // 4. CORS - Cross-Origin Resource Sharing
        cors.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");

        // 5. Disable DELETE and POST for Task
		/*
		 * config.getExposureConfiguration() .forDomainType(Task.class)
		 * .withItemExposure((metadata, httpMethods) ->
		 * httpMethods.disable(HttpMethod.DELETE)) .withCollectionExposure((metadata,
		 * httpMethods) -> httpMethods.disable(HttpMethod.POST));
		 */
    }
}