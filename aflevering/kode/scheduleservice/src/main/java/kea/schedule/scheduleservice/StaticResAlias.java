package kea.schedule.scheduleservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResAlias implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("addResourceHandlers");
        registry.addResourceHandler("/serviceresource/KEA-Schedule/**").addResourceLocations("classpath:/static/");
    }
}
