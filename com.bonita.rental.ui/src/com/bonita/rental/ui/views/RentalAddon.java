
package com.bonita.rental.ui.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.bonita.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;

/**
 * RentalAddon is an addon (present in the fragment.e4.xmi). His main goal is to
 * reference objects, registry into the {@link IEclipseContext}. He will be used
 * also to create an ImageRegistry and put all the pictures located in the same
 * bundle into this registry. Then, the registry will be send into the context
 * to be injected in parts/views.
 * 
 * @author mbource
 *
 */
public class RentalAddon implements RentalUIConstants {

	@PostConstruct
	public void applicationStarted(IEclipseContext context) {
		context.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
		context.set(RENTAL_UI_IMG_REGISTRY, getLocalImageRegistry());

	}

	private ImageRegistry getLocalImageRegistry() {
		// Method useful to get the Bundle where this class is loaded.
		Bundle b = FrameworkUtil.getBundle(getClass());

		ImageRegistry reg = new ImageRegistry();

		// getEntry from the bundle allow us to get the picture located inside the
		// bundle (using classic url like file:// would not work here).
		reg.put(IMG_CUSTOMER, ImageDescriptor.createFromURL(b.getEntry(IMG_CUSTOMER)));
		reg.put(IMG_RENTAL, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL)));
		reg.put(IMG_RENTAL_OBJECT, ImageDescriptor.createFromURL(b.getEntry(IMG_RENTAL_OBJECT)));
		reg.put(IMG_AGENCY, ImageDescriptor.createFromURL(b.getEntry(IMG_AGENCY)));

		return reg;
	}

}
