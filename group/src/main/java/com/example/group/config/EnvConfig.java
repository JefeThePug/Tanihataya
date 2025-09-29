package com.example.group.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvConfig {
	@Bean
	Dotenv dotenv() {
		return Dotenv.configure().load();
	}
}