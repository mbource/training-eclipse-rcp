
package com.bonita.rental.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;


public class RentalAddon {

	@PostConstruct
	public void applicationStarted(IEclipseContext context) {
		context.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
	}

}
