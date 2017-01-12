package de.novatec.bpm.example.pizza;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

import de.novatec.bpm.example.pizza.ServiceCall;

@Alternative
public class ServiceCallMockProducer {

	@Produces
	public ServiceCall produceServiceCall() {
		ServiceCall serviceCall = mock(ServiceCall.class);
		when(serviceCall.callService()).thenReturn("Mocked response");
		return serviceCall;
	}

}
