package com.mal.config;

import javax.inject.Inject;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.context.ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME;

@Configuration
@ComponentScan(basePackages={"com.mal.config"})
@EnableScheduling
public class SpringAppConfig {
	public final static String DEFAULT_PROP_FILE = "classpath:application.properties";

//	private Logger logger = LoggerFactory.getLogger(SpringAppConfig.class);

	/**
	 * Defines the name of bean for "propertyPlaceholder".<p>
	 */
	public final static String BEAN_NAME_PROP_PLACEHOLDER_CONF = "propertyPlaceholder";

	public SpringAppConfig() {}

	@Bean(name=BEAN_NAME_PROP_PLACEHOLDER_CONF)
	public static PropertySourcesPlaceholderConfigurer buildPlaceholderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}
