package com.mal.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.mal.config.ServiceConfig;
import static com.mal.config.SpringAppConfig.BEAN_NAME_PROP_PLACEHOLDER_CONF;

@Configuration
@Import({ServiceConfig.class })
public class ServiceTestConfig {
	public ServiceTestConfig() {}
	
	@Bean(name=BEAN_NAME_PROP_PLACEHOLDER_CONF)
	public static PropertySourcesPlaceholderConfigurer buildPlaceholderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
