package com.bonita.rental.ui.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;
import org.osgi.service.prefs.BackingStoreException;

import com.bonita.rental.ui.RentalUIConstants;

/**
 * Initializer used to make color set by default in Preferences menu (want first
 * time user or after using the restore default button).
 * 
 * @author mbource
 *
 */
public class RentalInitializer extends AbstractPreferenceInitializer implements RentalUIConstants {

	public RentalInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(PLUGIN_ID);

		if (node != null) {
			node.put(PREF_CUSTOMER_COLOR, StringConverter.asString(new RGB(0, 0, 255)));
			node.put(PREF_RENTAL_COLOR, StringConverter.asString(new RGB(255, 0, 0)));
			node.put(PREF_RENTAL_OBJECT_COLOR, StringConverter.asString(new RGB(0, 255, 0)));
			node.put(PREF_PALETTE, DEFAULT_PALETTE);

			node.put(PREF_DISPLAY_COUNT, StringConverter.asString(false));

			try {
				node.flush();
			} catch (BackingStoreException e) {

			}
		}

	}

}
