package com.siapri.broker.app.views.mainmenu;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;

public class ExitHandler {

	@Execute
	public void execute(final IWorkbench workbench) {
		//TODO : add dialog box to prevent the user that this will close the application
		workbench.close();
	}

}