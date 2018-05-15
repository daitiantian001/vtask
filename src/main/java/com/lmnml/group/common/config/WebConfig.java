package com.lmnml.group.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.lmnml.group.common.inter.LogInterceptor;
import com.lmnml.group.common.inter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by daitian on 2018/5/10.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/pdata/user/**","/app/**","/sys/user/login");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/platform", "/platform/html/home/login.html");
        registry.addRedirectViewController("/system", "/system/html/home/login.html");
        registry.addRedirectViewController("/api", "/swagger-ui.html");
        registry.addRedirectViewController("/error", "/index.html");
    }

    /**
     * 自定义消息转换器.
     *
     * @return
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(getBean()));
        return converter;
    }

    public BeanSerializerModifier getBean() {
        return new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                for (int i = 0; i < beanProperties.size(); i++) {
                    BeanPropertyWriter writer = beanProperties.get(i);
                    writer.assignNullSerializer(getJs());
                }
                return beanProperties;
            }
        };
    }

    public JsonSerializer getJs() {
        return new JsonSerializer() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                if (o == null) {
                    jsonGenerator.writeString("");
                } else {
                    jsonGenerator.writeObject(o);
                }
            }
        };
    }
}
