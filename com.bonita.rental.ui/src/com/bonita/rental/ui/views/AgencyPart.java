
package com.bonita.rental.ui.views;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.bonita.rental.ui.providers.RentalProvider;
import com.opcoach.training.rental.RentalAgency;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.SWT;

/**
 * Agency part : view displaying a tree containing Customers, rental and rented
 * items.
 * 
 * @author mbource
 *
 */
public class AgencyPart {

	@Inject
	private ESelectionService selectionService;

	@Inject
	public AgencyPart() {

	}

	@PostConstruct
	public void postConstruct(Composite parent, RentalAgency agency, IEclipseContext context, EMenuService menuService) {
		List<RentalAgency> agencies = new ArrayList<RentalAgency>();
		agencies.add(agency);
		parent.setLayout(new GridLayout(1, false));

		TreeViewer tv = new TreeViewer(parent);
		Tree tree = tv.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		// Never forget to put RentalProvider in the context or else the injected
		// objects he had will never be populated.
		RentalProvider rentalProvider = ContextInjectionFactory.make(RentalProvider.class, context);
		tv.setContentProvider(rentalProvider);
		tv.setLabelProvider(rentalProvider);
		tv.setInput(agencies);

		/**
		 * Each time a selection on the tree is done, this listener will call the
		 * ESelectionService to send the data of the item selected into this service.
		 * Then, other parts can get the content of this selection by subscribing to the
		 * event selection.
		 */
		tv.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = event.getStructuredSelection();

				selectionService.setSelection(sel.size() == 1 ? sel.getFirstElement() : sel.toArray());

			}
		});
		
		// Registering the popup menu to the menu service.
		menuService.registerContextMenu(tv.getControl(), "com.bonita.rental.ui.popupmenu.0");
		
		

	}

}