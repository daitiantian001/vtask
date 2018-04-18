package com.lmnml.group;

import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSwagger2Doc
public class VtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(VtaskApplication.class, args);
    }

}
