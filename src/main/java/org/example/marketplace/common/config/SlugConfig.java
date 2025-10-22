package org.example.marketplace.common.config;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class SlugConfig {

    @Bean
    public Slugify slugifyBuilder() {
        return Slugify.builder()
                .locale(Locale.forLanguageTag("ru"))
                .build();
    }
}
