package de.novatec.bpm.examples.pizza_spring;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.withVariables;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
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

/**
 * Test case starting an in-memory database-backed Process Engine.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemProcessEngineConfiguration.class })
public class ProcessTest {

	private static final String PROCESS_DEFINITION_KEY = "pizza";

	@Autowired
	ProcessEngine processEngine;

	@Rule
	@ClassRule
	public static ProcessEngineRule processEngineRule;

	@PostConstruct
	void initRule() {
		processEngineRule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
	}

	@Test
	@Deployment(resources = "process.bpmn")
	public void testHappyPath() {
		ProcessInstance processInstance = processEngineRule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
		assertThat(processInstance).isStarted().isWaitingAt("Task_1ini2fm");

		// complete the order task
		complete(task(), withVariables("pizzaType", "Funghi", "customerName", "John Doe"));
		assertThat(processInstance).isWaitingAt("Task_1vnq6m4");

		complete(task(), withVariables("approved", true));
		assertThat(processInstance).isWaitingAt("Task_03sm4yo");

		complete(task());
		assertThat(processInstance).isEnded();
	}

	@Test
	@Deployment(resources = "process.bpmn")
	public void testNotHappyPath() {
		ProcessInstance processInstance = processEngineRule.getRuntimeService()
				.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
		assertThat(processInstance).isStarted().isWaitingAt("Task_1ini2fm");

		// complete the order task
		complete(task(), withVariables("pizzaType", "Funghi", "customerName", "John Doe"));
		assertThat(processInstance).isWaitingAt("Task_1vnq6m4");

		complete(task(), withVariables("approved", false));
		assertThat(processInstance).isEnded();
	}
}
