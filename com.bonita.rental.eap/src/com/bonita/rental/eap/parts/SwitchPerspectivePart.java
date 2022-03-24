package com.bonita.rental.eap.parts;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

import com.bonita.rental.ui.RentalUIConstants;

/**
 * This part is only used to directly switch to the snippet perspective provided
 * in plugins in dependencies
 */
public class SwitchPerspectivePart {
	private static final String PERS_STACK = "com.bonita.rental.eap.perspectivestack.0";

	/*******
	 * WARNING. -->>>> MUST depend on org.eclipse.osgi.services to receive an Event
	 * from OSGI
	 */

	/**
	 * Must react on application start up complete to switch the perspective (it
	 * does not work (anymore) in the PostConstruct))...
	 */
	@Inject
	@Optional
	public void switchToStartPerspective(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) org.osgi.service.event.Event event,
			EPartService ps, EModelService ms, MApplication appli, MWindow refWin) {
		MPerspective mainPerspective = (MPerspective) ms.cloneSnippet(appli, RentalUIConstants.RENTAL_UI_PERSPECTIVE,
				refWin);

		// Add this perspective in the perspective Stack
		MPerspectiveStack pstack = (MPerspectiveStack) ms.find(PERS_STACK, appli);
		pstack.getChildren().add(mainPerspective);

		// Can now switch to this perspective
		ps.switchPerspective(mainPerspective);
	}

}