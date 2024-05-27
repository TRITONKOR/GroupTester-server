package com.tritonkor;

import com.tritonkor.domain.service.impl.ResultService;
import com.tritonkor.net.response.ResultResponse;
import com.tritonkor.persistence.AppConfig;
import com.tritonkor.persistence.entity.Result;
import com.tritonkor.persistence.util.DatabaseInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Main {

    public static AnnotationConfigApplicationContext springContext;

    public static void main(String[] args) {
        springContext = new AnnotationConfigApplicationContext(AppConfig.class);
        var databaseInitializer = springContext.getBean(DatabaseInitializer.class);
        ResultService service = springContext.getBean(ResultService.class);

        databaseInitializer.init();
        SpringApplication.run(Main.class, args);
    }
}
