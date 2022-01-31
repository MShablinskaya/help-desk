package com.innowise.training.shablinskaya.helpdesk.config;

import com.innowise.training.shablinskaya.helpdesk.converter.headers.HeaderToEnumConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.headers.RoleToEnumConverter;
import com.innowise.training.shablinskaya.helpdesk.converter.headers.StringToEnumConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.innowise.training.shablinskaya.helpdesk")
public class SpringConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToEnumConverter());
        registry.addConverter(new HeaderToEnumConverter());
        registry.addConverter(new RoleToEnumConverter());
    }

    //    @Bean
//    public CommonsMultipartResolver multipartResolver(){
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(5000000);
//        return multipartResolver;
//    }


//    @Bean
//    public BCryptPasswordEncoder BCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**");
//    }

}
