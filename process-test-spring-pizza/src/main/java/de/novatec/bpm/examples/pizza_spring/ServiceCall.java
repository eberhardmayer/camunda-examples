package de.novatec.bpm.examples.pizza_spring;

import java.util.logging.Logger;

public class ServiceCall {

	private final Logger LOGGER = Logger.getLogger(ServiceCall.class.getName());

	public String callService() {

		LOGGER.info("This bean will call a service");
		
		return "Normal response";
	}
}
