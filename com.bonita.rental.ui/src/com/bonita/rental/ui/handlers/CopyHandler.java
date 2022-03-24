package com.bonita.rental.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import com.bonita.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.Customer;

public class CopyHandler {

	@Inject
	@Named(RentalUIConstants.RENTAL_UI_IMG_REGISTRY)
	private ImageRegistry registry;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Customer customer, IEventBroker broker) {
		Clipboard clipboard = new Clipboard(Display.getCurrent());
		String textData = customer.getDisplayName();
		String rtfData = "{\\rtf1\\b\\i " + textData + "}";
		TextTransfer textTransfer = TextTransfer.getInstance();
		RTFTransfer rtfTransfer = RTFTransfer.getInstance();
		ImageTransfer imageTransfer = ImageTransfer.getInstance();

		Transfer[] transfers = new Transfer[] { textTransfer, rtfTransfer, imageTransfer };
		// Image will be only displayed with the right software (Word) with "paste as image..."
		Object[] data = new Object[] { textData, rtfData, registry.get(RentalUIConstants.IMG_AGENCY).getImageData() };
		clipboard.setContents(data, transfers);
		clipboard.dispose();
		
		// Sending the copy event to the e4 event bus.
		broker.send("rental/newCopy", customer);
	}

	@CanExecute
	@Evaluate
	public boolean evaluate(@Named(IServiceConstants.ACTIVE_SELECTION) Object object) {
		return object instanceof Customer;
	}

}
