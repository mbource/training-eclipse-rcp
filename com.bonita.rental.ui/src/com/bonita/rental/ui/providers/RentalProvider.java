package com.bonita.rental.ui.providers;

import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

public class RentalProvider extends LabelProvider implements ITreeContentProvider, IColorProvider {

	@Override
	public Color getForeground(Object element) {
		if (element != null && element instanceof RentalObject) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
		}

		if (element != null && element instanceof Customer) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		}

		if (element != null && element instanceof Rental) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
		}

		return null;
	}

	@Override
	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

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
			Node resultCustomers = new Node(((RentalAgency) parentElement), "Customers");
			Node resultLocations = new Node(((RentalAgency) parentElement), "Locations");
			Node resultItems = new Node(((RentalAgency) parentElement), "Objets à louer");

			result = List.of(resultCustomers, resultLocations, resultItems).toArray();
		}

		if (parentElement instanceof Node) {
			result = ((Node) parentElement).getChildren();
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
		return element instanceof RentalAgency || element instanceof Node;
	}

	@Override
	public String getText(Object element) {
		if (element != null && element instanceof RentalAgency) {
			return ((RentalAgency) element).getName();
		}

		if (element != null && element instanceof Customer) {
			return ((Customer) element).getDisplayName();
		}

		if (element != null && element instanceof RentalObject) {
			return ((RentalObject) element).getName();
		}

		if (element != null && element instanceof Node) {
			return ((Node) element).getLabel();
		}

		return super.getText(element);
	}

	class Node {
		private RentalAgency agency;
		private String label;

		public Node(RentalAgency agency, String label) {
			this.agency = agency;
			this.label = label;
		}

		public RentalAgency getAgency() {
			return agency;
		}

		public void setAgency(RentalAgency agency) {
			this.agency = agency;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Object[] getChildren() {
			if (label.equals("Customers")) {
				return this.agency.getCustomers().toArray();
			}

			if (label.equals("Locations")) {
				return this.agency.getRentals().toArray();
			}

			if (label.equals("Objets à louer")) {
				return this.agency.getObjectsToRent().toArray();
			}
			return null;
		}

	}

}
