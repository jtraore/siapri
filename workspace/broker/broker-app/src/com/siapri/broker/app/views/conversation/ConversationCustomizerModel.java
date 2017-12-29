package com.siapri.broker.app.views.conversation;

import java.util.Date;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.ZonedDateTimeToDateConverter;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Conversation;
import com.siapri.broker.business.model.Conversation.Direction;
import com.siapri.broker.business.model.Conversation.Type;

@Data
public class ConversationCustomizerModel extends AbstractCustomizerModel<Conversation> {
	
	@EntityProperty(converter = ZonedDateTimeToDateConverter.class)
	private Date date;

	@EntityProperty
	private Long duration;

	@EntityProperty
	private Direction direction;

	@EntityProperty
	private Type type;

	@EntityProperty
	private String subject;
	
	@EntityProperty
	private String description;

	@EntityProperty
	private Contract contract;

	public ConversationCustomizerModel() {
		this(null);
	}

	public ConversationCustomizerModel(final Conversation target) {
		super(target);
	}
}
