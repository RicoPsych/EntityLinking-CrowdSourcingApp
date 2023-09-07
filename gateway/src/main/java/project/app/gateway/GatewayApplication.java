package project.app.gateway;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


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

			.route(r -> r.path("/api/tasks/*/response/**")
				.uri("http://localhost:8087"))

			.route(r -> r.path("/api/response/*/selected_word/**")
				.uri("http://localhost:8088"))

			.route(r -> r.path("/api/response/*/raport/**")
				.uri("http://localhost:8089"))
			.build();
    }

	@Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

}
