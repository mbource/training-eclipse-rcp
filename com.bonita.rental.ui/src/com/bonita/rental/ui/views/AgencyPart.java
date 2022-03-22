
package com.bonita.rental.ui.views;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.bonita.rental.ui.providers.RentalProvider;
import com.opcoach.training.rental.RentalAgency;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.SWT;

public class AgencyPart {
	@Inject
	public AgencyPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent, RentalAgency agency, IEclipseContext context) {
		List<RentalAgency> agencies = new ArrayList<RentalAgency>();
		agencies.add(agency);
		parent.setLayout(new GridLayout(1, false));

		TreeViewer tv = new TreeViewer(parent);
		Tree tree = tv.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		//Never forget to put RentalProvider in the context or else the injected objects he had will never be populated.
		RentalProvider rentalProvider = ContextInjectionFactory.make(RentalProvider.class, context);
		tv.setContentProvider(rentalProvider);
		tv.setLabelProvider(rentalProvider);
		tv.setInput(agencies);

	}

}