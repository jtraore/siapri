package com.siapri.broker.app.views.conversation;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.action.IAction;
import com.siapri.broker.app.views.common.customizer.CustomizerDialog;
import com.siapri.broker.app.views.common.customizer.DialogBox;
import com.siapri.broker.app.views.common.customizer.DocumentList;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListActionModel;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Conversation;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class ConversationDataListModel extends DataListModel {

	private final Client client;

	public ConversationDataListModel(final Composite parent, final Client client) {
		this.client = client;
		inititalize(parent);
	}

	private void inititalize(final Composite parent) {

		labelProvider = new DataListLabelProvider();

		columnDescriptors = new ColumnDescriptor[7];
		columnDescriptors[0] = new ColumnDescriptor("Date", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Durée", 0.05, 125);
		columnDescriptors[2] = new ColumnDescriptor("Entrant/Sortant", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Type", 0.10, 125);
		columnDescriptors[4] = new ColumnDescriptor("Contrat", 0.20, 125);
		columnDescriptors[5] = new ColumnDescriptor("Sujet", 0.20, 125);
		columnDescriptors[6] = new ColumnDescriptor("Description", 0.20, 125);

		final IAction createAction = (event) -> {
			final Conversation conversation = new Conversation();
			conversation.setDate(ZonedDateTime.now());
			final String title = "Nouvelle conversation";
			final String description = String.format("Cette fenêtre permet de créer une nouvelle conversation");
			final ConversationCustomizer customizer = new ConversationCustomizer(conversation, title, description);
			final DocumentList documentList = new DocumentList(conversation.getDocuments());
			final DialogBox dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				return BundleUtil.getService(IBasicDaoService.class).save(conversation);
			}
			return null;
		};

		final IAction editAction = (event) -> {
			final Conversation conversation = (Conversation) event.getTarget();
			final String title = "Edition d'une conversation";
			final String description = String.format("Cette fenêtre permet d'éditer une conversation");
			final ConversationCustomizer customizer = new ConversationCustomizer(conversation, title, description);
			final DocumentList documentList = new DocumentList(conversation.getDocuments());
			final CustomizerDialog dialog = new CustomizerDialog(parent.getShell(), customizer, documentList);
			if (dialog.open() == Window.OK) {
				return BundleUtil.getService(IBasicDaoService.class).save(conversation);
			}
			return null;
		};

		final IAction deleteAction = (event) -> {
			final Conversation conversation = (Conversation) event.getTarget();
			BundleUtil.getService(IBasicDaoService.class).delete(conversation);
			return conversation;
		};

		actionModel = new DataListActionModel(createAction, editAction, deleteAction);

		dataList = new WritableList<>(new ArrayList<>(retrieveConversations()), Conversation.class);
	}

	private List<Conversation> retrieveConversations() {
		return BundleUtil.getService(DaoCacheService.class).getConversations(client);
	}

	private static final class DataListLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(final Object arg0, final int arg1) {
			return null;
		}

		@Override
		public String getColumnText(final Object object, final int column) {
			final Conversation conversation = (Conversation) object;
			switch (column) {
				case 0:
					return Util.DATE_TIME_FORMATTER.format(conversation.getDate());
				case 1:
					return conversation.getDuration() != null ? conversation.getDuration().toString() : "";
				case 2:
					return conversation.getDirection().getLabel();
				case 3:
					return conversation.getType().getLabel();
				case 4:
					return conversation.getContract().getNumber();
				case 5:
					return conversation.getSubject();
				case 6:
					return conversation.getDescription();
			}
			return null;
		}
	}
}