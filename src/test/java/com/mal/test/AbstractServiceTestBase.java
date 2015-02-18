package com.mal.test;

import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(
	classes={ServiceTestConfig.class, ObjectMapper.class}
)
@Test(suiteName="ServiceSuite")
public abstract class AbstractServiceTestBase extends AbstractDaoTestBase {
	protected AbstractServiceTestBase() {
		super();
	}
}
