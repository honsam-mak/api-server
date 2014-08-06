package com.mal.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/*
 * The base on Spring framework
 */
@ContextConfiguration(
    initializers=com.mal.config.AppContextIntializer.class
)
public class AbstractBasicSpringBase extends AbstractTestNGSpringContextTests {

	protected AbstractBasicSpringBase() {
		super();
	}
}
