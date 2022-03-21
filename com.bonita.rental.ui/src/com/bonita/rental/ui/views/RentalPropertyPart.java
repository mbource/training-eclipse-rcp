
package com.bonita.rental.ui.views;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.bonita.rental.core.RentalCoreActivator;
import com.opcoach.training.rental.Rental;

public class RentalPropertyPart {

	private Label rentedCustomer;
	private Label rentedItem;

	@Inject
	public RentalPropertyPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		Group infoGroup = new Group(parent, SWT.BORDER);
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(2, false));

		rentedItem = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedItem.setLayoutData(gd);

		rentedCustomer = new Label(infoGroup, SWT.BORDER);
		rentedCustomer.setLayoutData(gd);

		setRental(RentalCoreActivator.getAgency().getRentals().get(0));
	}

	public void setRental(Rental rental) {
		rentedItem.setText(rental.getRentedObject().getName());
		rentedCustomer.setText("Loué à :" + rental.getCustomer().getDisplayName());
	}

}