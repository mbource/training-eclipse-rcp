package com.bonita.rental.ui.providers;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;

public class RentalProvider extends LabelProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection) inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] result = null;
		if (parentElement instanceof RentalAgency) {
			result = ((RentalAgency) parentElement).getCustomers().toArray();
		}
		return result;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return element instanceof RentalAgency;
	}

	@Override
	public String getText(Object element) {
		if (element != null && element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		}

		if (element != null && element instanceof Customer) {
			return ((Customer) element).getDisplayName();
		}

		return null;
	}

}
