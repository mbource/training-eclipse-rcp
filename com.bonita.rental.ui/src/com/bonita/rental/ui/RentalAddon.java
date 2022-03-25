
package com.bonita.rental.ui;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IColorProvider;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.opcoach.e4.preferences.ScopedPreferenceStore;
import com.opcoach.training.rental.Customer;
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
	public void applicationStarted(IEclipseContext context, IExtensionRegistry reg) {
		// Here we put a sample Agency with a generator called from another
		// bundle/plugin (com.bonita.rental.core)
		// We can access this generator because the com.bonita.rental.core plugin has
		// allowed us to use it with exported package in his manifest.
		// We also need to add com.bonita.rental.core plugin in our required plugin of
		// our own manifest.
		context.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
		context.set(RENTAL_UI_IMG_REGISTRY, getLocalImageRegistry());
		context.set(RENTAL_UI_COLOR_REGISTRY, new ColorRegistry());
		IPreferenceStore prefStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);
		context.set(RENTAL_UI_PREF_STORE, prefStore);
		Map<String, Palette> palettes = getPalettes(reg, context);
		context.set(PALETTE_MANAGER, palettes);
		context.set(Palette.class, palettes.get(DEFAULT_PALETTE));



	}

	private Map<String, Palette> getPalettes(IExtensionRegistry reg, IEclipseContext context) {

		Map<String, Palette> paletteManager = new HashMap<>();
		try {
			for (IConfigurationElement element : reg.getConfigurationElementsFor("com.bonita.rental.ui.palette")) {
				Bundle b = Platform.getBundle(element.getNamespaceIdentifier());
				Class<?> clazz = b.loadClass(element.getAttribute("paletteClass"));

				paletteManager.put(element.getAttribute("id"), new Palette(element.getAttribute("id"),
						element.getAttribute("name"), (IColorProvider) ContextInjectionFactory.make(clazz, context)));
				System.out.println(paletteManager.get(element.getAttribute("id")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return paletteManager;
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

	@Inject
	@Optional
	public void reactOnCopyEvent(@UIEventTopic("rental/newCopy") Customer customerCopied) {
		System.out.println("The customer " + customerCopied.getDisplayName() + " has been copied!");

	}

	/**
	 * Display all the fragments and processors from the extensionpoint
	 * org.eclipse.e4.workbench.model.
	 * 
	 * @param reg The {@link IExtensionRegistry} injected to get.
	 */
	@Inject
	public void getExtensions(IExtensionRegistry reg) {
		IExtensionPoint extp = reg.getExtensionPoint("org.eclipse.e4.workbench.model");
		IExtension[] extensions = extp.getExtensions();

		for (IExtension ext : extensions) {
			for (IConfigurationElement config : ext.getConfigurationElements()) {
				if (config.getAttribute("uri") != null) {
					System.out.println("Model fragment " + config.getAttribute("uri") + " found in "
							+ config.getNamespaceIdentifier());
				}
				if (config.getAttribute("class") != null) {
					System.out.println("Model processor " + config.getAttribute("class") + " found in "
							+ config.getNamespaceIdentifier());
				}

			}
		}
	}
}