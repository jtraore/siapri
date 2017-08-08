package com.ngosdi.lawyer.app.views.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.siapri.broker.business.model.Document;

public final class Util {
	
	public static Image getDefaultProgramImage(final Document document) {
		final File file = new File(document.getPath());
		if (file.exists()) {
			final Program defaultProgram = Program.findProgram(FilenameUtils.getExtension(file.getName()));
			if (defaultProgram != null) {
				final ImageData imageData = defaultProgram.getImageData();
				return new Image(Display.getCurrent(), imageData);
			}
		}
		return null;
	}
	
	public static void buildMenuItem(final Menu menu, final String name, final Image image, final SelectionListener listener) {
		final MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText(name);
		item.setImage(image);
		item.addSelectionListener(listener);
	}
	
	public static void openDocument(final Document document) {
		final File file = new File(document.getPath());
		if (file.exists()) {
			if (!Program.launch(document.getPath())) {
				MessageDialog.openError(Display.getCurrent().getActiveShell(), "Overture de document", String.format("Impossible d'ouvrir %s", document.getPath()));
			}
		} else {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Overture de document", String.format("%s n'existe pas", document.getPath()));
		}
	}
	
	public static List<Document> selectDocuments() {
		final FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.MULTI);
		fileDialog.setText("Sélectionnez les fichiers à joindre");
		fileDialog.setFilterPath(System.getProperty("user.home"));
		fileDialog.setFilterExtensions(new String[] { "*.*" });
		final String selectedFile = fileDialog.open();
		if (selectedFile != null) {
			final String selectedFolder = new File(selectedFile).getParent() + File.separator;
			return Arrays.asList(fileDialog.getFileNames()).stream().map(fileName -> new Document(selectedFolder + fileName, "")).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
}
