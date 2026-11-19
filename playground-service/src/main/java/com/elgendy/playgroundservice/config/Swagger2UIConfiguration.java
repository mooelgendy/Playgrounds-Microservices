package com.elgendy.playgroundservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Serves the Swagger UI assets shipped with springfox.
 *
 * <p>Replaced the deprecated {@code WebMvcConfigurerAdapter} and added the
 * missing {@code @Configuration}; without it this class was never picked up
 * because it is not annotated (and {@code @EnableWebMvc} on a plain class has
 * no effect).</p>
 */
@Configuration
public class Swagger2UIConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
