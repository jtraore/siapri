package com.ngosdi.lawyer.app.views.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;

public class GeneralPreferencePage extends AbstractFieldEditorPreferencePage {

	public GeneralPreferencePage() {
		super("Général");
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(EPreferenceKey.AUTO_CONNECTION.name(), "&Connecter automatiquement sans authentification", getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(EPreferenceKey.CURRENCY.name(), "Choix de la devise pour les opérations monétaires", 1, new String[][] { { "&Dinar", "DINAR" }, { "&Euro", "EURO" } },
				getFieldEditorParent()));
	}
}
