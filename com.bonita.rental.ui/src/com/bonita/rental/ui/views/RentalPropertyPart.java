
package com.bonita.rental.ui.views;

import javax.inject.Inject;
import javax.inject.Named;
import javax.annotation.PostConstruct;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;

public class RentalPropertyPart {

	private Label rentedCustomer;
	private Label rentedItem;
	private Group grpDates;
	private Label startTitle;
	private Label startValue;
	private Label endTitle;
	private Label endValue;

	@Inject
	public RentalPropertyPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent, RentalAgency rentalAgency) {
		parent.setLayout(new GridLayout(1, false));
		Group infoGroup = new Group(parent, SWT.BORDER);
		infoGroup.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		infoGroup.setText("Informations");
		infoGroup.setLayout(new GridLayout(2, false));

		rentedItem = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedItem.setLayoutData(gd);

		rentedCustomer = new Label(infoGroup, SWT.BORDER);
		new Label(infoGroup, SWT.NONE);

		grpDates = new Group(parent, SWT.NONE);
		grpDates.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpDates.setText("Dates");
		grpDates.setLayout(new GridLayout(2, false));

		startTitle = new Label(grpDates, SWT.NONE);
		startTitle.setText("New Label");

		startValue = new Label(grpDates, SWT.NONE);
		startValue.setText("New Label");

		endTitle = new Label(grpDates, SWT.NONE);
		endTitle.setText("New Label");

		endValue = new Label(grpDates, SWT.NONE);
		endValue.setText("New Label");

		setRental(rentalAgency.getRentals().get(0));
	}

	public void setRental(Rental rental) {
		if (rental != null) {
			rentedItem.setText(rental.getRentedObject().getName());
			rentedCustomer.setText("Loué à :" + rental.getCustomer().getDisplayName());
			startValue.setText(rental.getStartDate().toString());
			endValue.setText(rental.getEndDate().toString());
		}
	}

	@Inject
	@Optional
	public void receiveSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Rental rental) {
		setRental(rental);
	}
}