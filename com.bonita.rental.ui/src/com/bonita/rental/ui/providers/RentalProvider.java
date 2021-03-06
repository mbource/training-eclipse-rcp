package com.bonita.rental.ui.providers;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.bonita.rental.ui.Palette;
import com.bonita.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalObject;

/**
 * RentalProvider contain providers for building a Tree
 * ({@link ITreeContentProvider}) and using specific Colors
 * ({@link IColorProvider}) and Label ({@link LabelProvider}) on that Tree. This
 * class will be then used as a structure for a view/part using a widget JFace
 * ({@link TreeViewer}).
 * 
 * @author mbource
 *
 */
public class RentalProvider extends LabelProvider implements ITreeContentProvider, IColorProvider {

	private static final String NODE_OBJETS_A_LOUER = RentalUIConstants.OBJECTS_NODE;
	private static final String NODE_LOCATIONS = RentalUIConstants.RENTALS_NODE;
	private static final String NODE_CUSTOMERS = RentalUIConstants.CUSTOMERS_NODE;

	@Inject
	@Named(RentalUIConstants.RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry registry;

	@Inject
	@Named(RentalUIConstants.RENTAL_UI_COLOR_REGISTRY)
	private ColorRegistry colorRegistry;

	@Inject
	@Named(RentalUIConstants.RENTAL_UI_PREF_STORE)
	private IPreferenceStore prefStore;
	
	@Optional
	@Inject
	private Palette palette;

	@Override
	public Color getForeground(Object element) {
		return palette == null ? null : palette.getProvider().getForeground(element);
	}

	@Override
	public Color getBackground(Object element) {
		return palette == null ? null : palette.getProvider().getBackground(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element != null && element instanceof Node) {
			if (((Node) element).getLabel().equals(NODE_CUSTOMERS)) {
				return registry.get(RentalUIConstants.IMG_CUSTOMER);
			}

			if (((Node) element).getLabel().equals(NODE_LOCATIONS)) {
				return registry.get(RentalUIConstants.IMG_RENTAL);
			}

			if (((Node) element).getLabel().equals(NODE_OBJETS_A_LOUER)) {
				return registry.get(RentalUIConstants.IMG_RENTAL_OBJECT);
			}
		}

		if (element != null && element instanceof RentalAgency) {
			return registry.get(RentalUIConstants.IMG_AGENCY);
		}

		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] result = null;
		if (parentElement instanceof RentalAgency) {
			Node resultCustomers = new Node(((RentalAgency) parentElement), NODE_CUSTOMERS);
			Node resultLocations = new Node(((RentalAgency) parentElement), NODE_LOCATIONS);
			Node resultItems = new Node(((RentalAgency) parentElement), NODE_OBJETS_A_LOUER);

			result = List.of(resultCustomers, resultLocations, resultItems).toArray();
		}

		if (parentElement instanceof Node) {
			result = ((Node) parentElement).getChildren();
		}

		return result;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
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
			if (label.equals(NODE_CUSTOMERS)) {
				return this.agency.getCustomers().toArray();
			}

			if (label.equals(NODE_LOCATIONS)) {
				return this.agency.getRentals().toArray();
			}

			if (label.equals(NODE_OBJETS_A_LOUER)) {
				return this.agency.getObjectsToRent().toArray();
			}
			return null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(agency, label);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Objects.equals(agency, other.agency) && Objects.equals(label, other.label);
		}

		private RentalProvider getEnclosingInstance() {
			return RentalProvider.this;
		}

	}

}
