package de.novatec.bpm.examples.pizza_spring;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

public class LoggerDelegate implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	@Autowired
	private ServiceCall serviceCall;

	public void execute(DelegateExecution execution) throws Exception {

		LOGGER.info("\n\n  ... LoggerDelegate invoked by " + "processDefinitionId=" + execution.getProcessDefinitionId()
				+ ", activtyId=" + execution.getCurrentActivityId() + ", activtyName='"
				+ execution.getCurrentActivityName() + "'" + ", processInstanceId=" + execution.getProcessInstanceId()
				+ ", businessKey=" + execution.getProcessBusinessKey() + ", executionId=" + execution.getId()
				+ " \n\n");

		String respose = serviceCall.callService();
		LOGGER.info(respose);
	}
}
