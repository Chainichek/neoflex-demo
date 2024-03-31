package ru.chainichek.neoflexdemo.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ru.chainichek.neoflexdemo.domain", "ru.chainichek.neoflexdemo.data"})
public class SpringConfig {
}
