package com.onekin.customdiff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.onekin.customdiff.config.MvcConfig;
import com.onekin.customdiff.controller.ControllerMarker;
import com.onekin.customdiff.repository.RepositoryMarker;
import com.onekin.customdiff.service.ServiceMarker;


@EnableAutoConfiguration
@ComponentScan(basePackageClasses= {ControllerMarker.class, ServiceMarker.class, RepositoryMarker.class})
@Import(MvcConfig.class)
public class CustomDiffApplication extends SpringBootServletInitializer {
 
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CustomDiffApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(CustomDiffApplication.class, args);
	}
}
