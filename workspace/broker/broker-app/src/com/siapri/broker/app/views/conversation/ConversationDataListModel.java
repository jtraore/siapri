package com.siapri.broker.app.views.conversation;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.siapri.broker.app.BundleUtil;
import com.siapri.broker.app.views.common.Util;
import com.siapri.broker.app.views.common.customizer.ICustomizer;
import com.siapri.broker.app.views.common.datalist.ColumnDescriptor;
import com.siapri.broker.app.views.common.datalist.DataListModel;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Conversation;
import com.siapri.broker.business.service.impl.DaoCacheService;

public class ConversationDataListModel extends DataListModel<Conversation> {
	
	private final Client client;
	
	public ConversationDataListModel(final Composite parent, final Client client) {
		super(false);
		this.client = client;
		initialize();
	}
	
	@Override
	protected void initialize() {

		super.initialize();
		
		labelProvider = new DataListLabelProvider();
		
		columnDescriptors = new ColumnDescriptor[7];
		columnDescriptors[0] = new ColumnDescriptor("Date", 0.15, 125);
		columnDescriptors[1] = new ColumnDescriptor("Durée", 0.05, 125);
		columnDescriptors[2] = new ColumnDescriptor("Entrant/Sortant", 0.10, 125);
		columnDescriptors[3] = new ColumnDescriptor("Type", 0.10, 125);
		columnDescriptors[4] = new ColumnDescriptor("Contrat", 0.20, 125);
		columnDescriptors[5] = new ColumnDescriptor("Sujet", 0.20, 125);
		columnDescriptors[6] = new ColumnDescriptor("Description", 0.20, 125);
	}
	
	@Override
	protected List<Conversation> loadElements() {
		return BundleUtil.getService(DaoCacheService.class).getConversations(client);
	}
	
	@Override
	protected ICustomizer<Conversation> createCustomizer(final Conversation element, final String title, final String description) {
		return new ConversationCustomizer(element, title, description);
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
