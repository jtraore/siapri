package com.siapri.broker.app.views.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.customizer.propertybinding.converter.ZonedDateTimeToDateConverter;
import com.siapri.broker.business.model.Client;
import com.siapri.broker.business.model.Contract;
import com.siapri.broker.business.model.InsuranceSubjectAttribute;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.WarrantyFormula;

public class ContractCustomizerModel extends AbstractCustomizerModel<Contract> {

	@EntityProperty
	private String number;

	@EntityProperty(converter = ZonedDateTimeToDateConverter.class)
	private Date subscriptionDate;

	@EntityProperty
	private Client client;

	@EntityProperty
	private WarrantyFormula warrantyFormula;

	private InsuranceType insuranceType;

	private List<SubjectAttributeValue> attributeValues;

	public ContractCustomizerModel() {
		this(null, null);
	}

	public ContractCustomizerModel(final Contract target, final InsuranceType insuranceType) {
		super(target);
		this.insuranceType = insuranceType;
		updateAttributeValues();
	}
	
	@Override
	public void validate() {
		super.validate();
		target.getSubjectAttributeCodes().clear();
		attributeValues.forEach(av -> target.getSubjectAttributeCodes().put(av.getAttribute().getCode(), av.getValue()));
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(final String number) {
		this.number = number;
	}
	
	public Date getSubscriptionDate() {
		return subscriptionDate;
	}
	
	public void setSubscriptionDate(final Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(final Client client) {
		this.client = client;
	}
	
	public InsuranceType getInsuranceType() {
		return insuranceType;
	}
	
	public void setInsuranceType(final InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
		updateAttributeValues();
	}

	public WarrantyFormula getWarrantyFormula() {
		return warrantyFormula;
	}

	public void setWarrantyFormula(final WarrantyFormula warrantyFormula) {
		this.warrantyFormula = warrantyFormula;
	}

	public List<SubjectAttributeValue> getAttributeValues() {
		return attributeValues;
	}

	private void updateAttributeValues() {
		if (insuranceType != null) {
			attributeValues = insuranceType.getAttributes().stream().map(SubjectAttributeValue::new).collect(Collectors.toList());
			attributeValues.forEach(av -> {
				final String value = target.getSubjectAttributeCodes().get(av.getAttribute().getCode());
				if (value != null) {
					av.setValue(value);
				}
			});
		} else {
			attributeValues = new ArrayList<>();
		}
	}

	public static class SubjectAttributeValue {

		private final InsuranceSubjectAttribute attribute;
		private String value;
		
		public SubjectAttributeValue(final InsuranceSubjectAttribute attribute) {
			this.attribute = attribute;
		}

		public InsuranceSubjectAttribute getAttribute() {
			return attribute;
		}
		
		public String getValue() {
			return value;
		}

		public void setValue(final String value) {
			this.value = value;
		}
	}
	
}
