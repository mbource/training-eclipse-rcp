package com.bonita.rental.adapters;

import org.eclipse.core.runtime.IAdapterFactory;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;

public class RentalToCustomerAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		Customer result = null;
		if ((adaptableObject instanceof Rental) && (adapterType == Customer.class)) {
			result = ((Rental) adaptableObject).getCustomer();
		}
		return (T) result;
	}

	@Override
	public Class<?>[] getAdapterList() {
		// TODO Auto-generated method stub
		return new Class[] { Customer.class };
	}

}
