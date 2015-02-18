package com.mal.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.mal.service.emp"})
public class EmpServiceConfig {
	public EmpServiceConfig() {}
}
