package de.novatec.bpm.example.pizza.interceptor;

import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Log
public class LoggingInterceptor {

	private static final Logger logger = Logger.getLogger(LoggingInterceptor.class.getName());

	@AroundInvoke
	public Object logMethodEntry(InvocationContext ctx) throws Exception {
		logger.info("Log via Interceptor: Entering method: " + ctx.getMethod().getName());
		return ctx.proceed();
	}
}
