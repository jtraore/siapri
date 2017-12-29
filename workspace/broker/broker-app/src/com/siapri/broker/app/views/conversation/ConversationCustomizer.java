package com.siapri.broker.app.views.conversation;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizer;
import com.siapri.broker.app.views.common.customizer.IValidationSupport;
import com.siapri.broker.app.views.common.customizer.ObjectSeekComposite;
import com.siapri.broker.app.views.common.customizer.SearchContext;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.app.views.common.proxy.ProxyFactory;
import com.siapri.broker.app.views.contract.ContractDataListModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Company;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Conversation;
import com.siapri.broker.business.model.Conversation.Direction;
import com.siapri.broker.business.model.Conversation.Type;
import com.siapri.broker.business.model.ILabelProvider;
import com.siapri.broker.business.model.Person;

public class ConversationCustomizer extends AbstractCustomizer<Conversation> {

	private final ConversationCustomizerModel customizerModel;

	public ConversationCustomizer(final Conversation object, final String title, final String description) {
		super(object, title, description);
		customizerModel = ProxyFactory.createProxy(new ConversationCustomizerModel(object));
	}

	@Override
	public Composite createArea(final Composite parent, final int style) {
		
		parent.setLayout(new GridLayout());
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout(2, false);
		composite.setLayout(gridLayout);
		
		final Label contractLabel = new Label(composite, SWT.NONE);
		contractLabel.setText("Contrat: ");

		final LabelProvider contractLabelProvider = new LabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof Contract) {
					final Contract contract = (Contract) element;
					final Client client = contract.getClient();
					if (client instanceof Person) {
						return String.format("%s - %s %s", contract.getNumber(), ((Person) client).getFirstName(), ((Person) client).getLastName());
					}
					return String.format("%s - %s", contract.getNumber(), ((Company) client).getName());
				}
				return "";
			}
		};
		final DataListModel contractListModel = new ContractDataListModel(parent);
		contractListModel.setSelectionEventActivated(false);
		final SearchContext contractSearchContext = new SearchContext(contractListModel, contractLabelProvider, "Recherche contrat", "Cette fenetre permet de rechercher un contrat");
		final ObjectSeekComposite contractSeekComposite = new ObjectSeekComposite(composite, contractSearchContext);
		contractSeekComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		bindingSupport.bindObjectSeekComposite(customizerModel, "contract", contractSeekComposite, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		final Label dateLabel = new Label(composite, SWT.NONE);
		dateLabel.setText("Date: ");
		final DateTime dateField = new DateTime(composite, SWT.BORDER | SWT.DATE | SWT.DROP_DOWN);
		dateField.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		final GridData gridData = new GridData(SWT.FILL, SWT.DEFAULT, true, false);
		dateField.setLayoutData(gridData);
		bindingSupport.bindDateTimeChooserComboWidget(customizerModel, "date", dateField, IValidationSupport.NON_EMPTY_VALIDATOR);

		final Label durationLabel = new Label(composite, SWT.NONE);
		durationLabel.setText("Durée: ");
		final Text durationText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "duration", durationText);
		durationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Label directionLabel = new Label(composite, SWT.NONE);
		directionLabel.setText("Entrant/Sortant: ");
		final ComboViewer directionComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		directionComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		directionComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((ILabelProvider) element).getLabel();
			}
		});
		directionComboViewer.setInput(Direction.values());
		bindingSupport.bindComboViewer(customizerModel, "direction", directionComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		final Label genderLabel = new Label(composite, SWT.NONE);
		genderLabel.setText("Type: ");
		final ComboViewer typeComboViewer = new ComboViewer(composite, SWT.READ_ONLY);
		typeComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		typeComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((ILabelProvider) element).getLabel();
			}
		});
		typeComboViewer.setInput(Type.values());
		bindingSupport.bindComboViewer(customizerModel, "type", typeComboViewer, IValidationSupport.NON_EMPTY_VALIDATOR);
		
		final Label subjectLabel = new Label(composite, SWT.NONE);
		subjectLabel.setText("Sujet: ");
		subjectLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false));
		final Text subjectText = new Text(composite, SWT.BORDER);
		bindingSupport.bindText(customizerModel, "subject", subjectText, IValidationSupport.NON_EMPTY_VALIDATOR);
		subjectText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Label descriptionLabel = new Label(composite, SWT.NONE);
		descriptionLabel.setText("description: ");
		descriptionLabel.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false));
		final Text descriptionText = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		bindingSupport.bindText(customizerModel, "description", descriptionText);
		final GridData descriptionTextGridData = new GridData(GridData.FILL_HORIZONTAL);
		descriptionTextGridData.heightHint = 120;
		descriptionText.setLayoutData(descriptionTextGridData);

		return composite;
	}

	@Override
	public void validateUpdate() {
		customizerModel.validate();
	}

	@Override
	public void cancelUpdate() {
	}
	
}
