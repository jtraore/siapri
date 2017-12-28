package com.siapri.broker.app.views.conversation;

import java.time.ZonedDateTime;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.ZonedDateTimeToDateConverter;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.Conversation;
import com.siapri.broker.business.model.Conversation.Direction;
import com.siapri.broker.business.model.Conversation.Type;

public class ConversationCustomizerModel extends AbstractCustomizerModel<Conversation> {

	@EntityProperty(converter = ZonedDateTimeToDateConverter.class)
	private ZonedDateTime date;
	
	@EntityProperty
	private Long duration;
	
	@EntityProperty
	private Direction direction;
	
	@EntityProperty
	private Type type;

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
