package com.bonita.rental.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class RentalCoreActivator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		RentalCoreActivator.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		RentalCoreActivator.context = null;
	}

}
