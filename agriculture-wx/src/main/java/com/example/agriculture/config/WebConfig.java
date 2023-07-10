package com.example.agriculture.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chenliming
 * @date 2023/2/3 15:25
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.video.location}")
    String videoPath;
    @Value("${upload.image.location}")
    String imagePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源映射{}",videoPath);
        registry.addResourceHandler("/video/**")
                .addResourceLocations("file:"+videoPath);
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:"+imagePath);
    }
}
