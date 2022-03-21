 
package com.bonita.rental.ui.views;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;

public class RentalPropertyPart {
	@Inject
	public RentalPropertyPart() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Label l = new Label(parent, SWT.BORDER);
		l.setText("tedsdsdsdsds");
		
		
	}
	
	
	public void setRental(Rental rental) {
		
	}
	
	
	
}