package de.novatec.bpm.example.pizza;

import java.util.logging.Logger;

import javax.inject.Named;

@Named
public class ServiceCall {

	private final Logger LOGGER = Logger.getLogger(ServiceCall.class.getName());

	public String callService() {

		LOGGER.info("This bean will call a service");
		
		return "Normal response";
	}
}
