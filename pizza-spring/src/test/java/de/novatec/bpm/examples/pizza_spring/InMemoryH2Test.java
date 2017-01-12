package de.novatec.bpm.examples.pizza_spring;

import javax.annotation.PostConstruct;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemProcessEngineConfiguration.class })
public class InMemoryH2Test {

	@Autowired
	ProcessEngine processEngine;

	@Rule
	@ClassRule
	public static ProcessEngineRule processEngineRule;

	@PostConstruct
	void initRule() {
		processEngineRule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
	}

	static {
		LogFactory.useSlf4jLogging(); // MyBatis
	}

	/**
	 * Just tests if the process definition is deployable.
	 */
	@Test
	@Deployment(resources = "process.bpmn")
	public void testParsingAndDeployment() {
		// nothing is done here, as we just want to check for exceptions during
		// deployment
	}
}
