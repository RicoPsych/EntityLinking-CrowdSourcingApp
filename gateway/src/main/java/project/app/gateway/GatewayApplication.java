package project.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

			.route("named_entities",r -> r.path("/api/texts/*/entities/**")
				.uri("http://localhost:8084"))

			.route("texts",r -> r.path("/api/texts/**")
				.uri("http://localhost:8081"))

			.route("ne_types",r -> r.path("/api/netypes/**")
				.uri("http://localhost:8083"))

			.route("tasks",r -> r.path("/api/task_sets/*/tasks/**")
				.uri("http://localhost:8086"))

			.route("task_sets",r -> r.path("/api/task_sets/**")
				.uri("http://localhost:8085"))

			.route("tags",r -> r.path("/api/tags/**")
				.uri("http://localhost:8082"))

			// .route(r -> r.path("/api/task_sets/**")
			// 	.uri("http://localhost:8085")
			// 	.id("task_sets"))
			.build();
    }
}
