package com.bonita.rental.ui.palette;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.swt.graphics.Color;

import com.bonita.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalObject;

public class DefaultPalette implements IColorProvider {

	@Inject
	@Named(RentalUIConstants.RENTAL_UI_COLOR_REGISTRY)
	private ColorRegistry colorRegistry;

	@Inject
	@Named(RentalUIConstants.RENTAL_UI_PREF_STORE)
	private IPreferenceStore prefStore;

	public DefaultPalette() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Color getForeground(Object element) {
		if (element != null && element instanceof RentalObject) {
			return getPrefColor(RentalUIConstants.PREF_RENTAL_OBJECT_COLOR);
		}

		if (element != null && element instanceof Customer) {
			return getPrefColor(RentalUIConstants.PREF_CUSTOMER_COLOR);
		}

		if (element != null && element instanceof Rental) {
			return getPrefColor(RentalUIConstants.PREF_RENTAL_COLOR);
		}

		return null;
	}

	@Override
	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	private Color getPrefColor(String key) {
		String rgbKey = prefStore.getString(key);

		Color result = colorRegistry.get(rgbKey);
		if (result == null) {
			// Get value in pref store
			colorRegistry.put(rgbKey, StringConverter.asRGB(rgbKey));
			result = colorRegistry.get(rgbKey);
		}

		return result;

	}

}
