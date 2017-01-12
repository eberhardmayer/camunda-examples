package de.novatec.bpm.examples.pizza_spring;

import java.util.logging.Logger;

import de.novatec.bpm.examples.pizza_spring.ServiceCall;

public class ServiceCallMock extends ServiceCall {

	private final Logger LOGGER = Logger.getLogger(ServiceCallMock.class.getName());

	@Override
	public String callService() {

		LOGGER.info("This bean cannot call a service");

		return "Mock response";
	}

}
