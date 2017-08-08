package com.ngosdi.lawyer.app.views.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;

public class ReportPreferencePage extends AbstractFieldEditorPreferencePage {

	public ReportPreferencePage() {
		super("Rapports");
	}

	@Override
	protected void createFieldEditors() {
		final DirectoryFieldEditor reportPathField = new DirectoryFieldEditor(EPreferenceKey.REPORT_PATH.name(), "&Répertoire de génération:", getFieldEditorParent());
		reportPathField.setChangeButtonText("Parcourir...");
		reportPathField.setStringValue(System.getProperty("user.home"));
		addField(reportPathField);
		addField(new BooleanFieldEditor(EPreferenceKey.REPORT_PREVIEW.name(), "&Afficher l'aperçu des rapports avant impression", getFieldEditorParent()));
		addField(new BooleanFieldEditor(EPreferenceKey.REPORT_DEFAULT_PRINTING.name(), "&Imprimer directement les rapports sur l'imprimante par défaut", getFieldEditorParent()));
	}
}
