package com.koreait.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("main/index"); // 템플릿 만들기 위해 임시 연동
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 정적 경로 설정
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + fileUploadPath);
    }
}
