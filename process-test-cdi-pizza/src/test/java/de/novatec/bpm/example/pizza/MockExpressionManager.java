package de.novatec.bpm.example.pizza;

import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.el.VariableScopeElResolver;
import org.camunda.bpm.engine.impl.javax.el.*;
import org.camunda.bpm.engine.test.mock.MockElResolver;

import java.beans.FeatureDescriptor;
import java.util.Iterator;

/**
 * Defines all together with the default Camunda ELResolver mock
 * implementations, a custom EL Resolver in order to access deployed beans
 * inside the adjacent CDI BeanContainer during the test execution.
 * <p>
 * </p>
 * <b> Note: MockExpressionManager will be hooked into the runtime of the
 * embedded Camunda BPM instance via the camunda test configuration file
 * "src/test/resources/camunda.cfg.xml".
 */
public class MockExpressionManager extends ExpressionManager {

	@Override
	protected ELResolver createElResolver() {
		CompositeELResolver compositeElResolver = new CompositeELResolver();
		compositeElResolver.add(new WeldELResolver());
		compositeElResolver.add(new VariableScopeElResolver());
		compositeElResolver.add(new MockElResolver());
		compositeElResolver.add(new ArrayELResolver());
		compositeElResolver.add(new ListELResolver());
		compositeElResolver.add(new MapELResolver());
		compositeElResolver.add(new BeanELResolver());
		return compositeElResolver;
	}

	/**
	 * This custom ELResolver implementation make use of DeltaSpikes
	 * BeanProvider API in order to create a linkage between the Camunda BPM
	 * engine and the CDI Weld-SE container. <b></b> The callback
	 * {@code getValue()} returns for a specified EL expression a corresponding
	 * container-managed CDI bean instance.
	 * <p>
	 * </p>
	 * <b> Note: in order to indicate the successful EL resolution, the property
	 * {@code isResolved} inside the passed ELContext must be set to true.
	 */
	private static class WeldELResolver extends ELResolver {

		@Override
		public Class<?> getCommonPropertyType(ELContext context, Object base) {
			return Object.class;
		}

		@Override
		public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
			return null;
		}

		@Override
		public Class<?> getType(ELContext context, Object base, Object property) {
			return null;
		}

		@Override
		public Object getValue(ELContext context, Object base, Object property) {
			Object contextualReference = BeanProvider.getContextualReference((String) property, true);
			if (contextualReference != null) {
				context.setPropertyResolved(true);
			}
			return contextualReference;
		}

		@Override
		public boolean isReadOnly(ELContext context, Object base, Object property) {
			return false;
		}

		@Override
		public void setValue(ELContext context, Object base, Object property, Object value) {
		}
	}
}