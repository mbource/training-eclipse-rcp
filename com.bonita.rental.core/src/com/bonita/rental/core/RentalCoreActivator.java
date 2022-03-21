package com.bonita.rental.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;

public class RentalCoreActivator implements BundleActivator {

	private static BundleContext context;

	private static RentalAgency a = RentalAgencyGenerator.createSampleAgency();

	static RentalAgency getAgency() {
		return a;
	}

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
