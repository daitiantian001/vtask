package com.lmnml.group;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableSwagger2Doc
@EnableScheduling
public class VtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(VtaskApplication.class, args);
    }

}
