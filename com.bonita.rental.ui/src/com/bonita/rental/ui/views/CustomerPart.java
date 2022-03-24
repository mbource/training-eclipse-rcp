
package com.bonita.rental.ui.views;

import javax.inject.Inject;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.adapter.Adapter;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.Rental;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class CustomerPart {
	private Label client;
	private Label resultClient;

	@Inject
	public CustomerPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent) {

		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setText("Informations Customer");
		infoGroup.setLayout(new GridLayout(1, false));

		client = new Label(infoGroup, SWT.NONE);
		client.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		client.setText("Customer is ");

		resultClient = new Label(infoGroup, SWT.NONE);
		resultClient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		resultClient.setSize(0, 20);

	}

	@Inject
	@Optional
	public void receiveSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Rental rental, Adapter adapter) {
		// Displaying the result into our view.
		Customer customer = adapter.adapt(rental, Customer.class);

		if (customer != null) {
			resultClient.setText(customer.getDisplayName());
		}
	}
}