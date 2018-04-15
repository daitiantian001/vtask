package com.lmnml.group;

import com.didispace.swagger.EnableSwagger2Doc;
import com.lmnml.group.entity.response.R;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableSwagger2Doc
public class VtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(VtaskApplication.class, args);
    }

    @Bean
    public R getR() {
        return new R();
    }
}
