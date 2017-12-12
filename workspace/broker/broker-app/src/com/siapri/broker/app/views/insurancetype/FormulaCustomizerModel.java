package com.siapri.broker.app.views.insurancetype;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.app.views.common.customizer.propertybinding.EntityProperty;
import com.siapri.broker.app.views.common.proxy.Data;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

@Data
public class FormulaCustomizerModel extends AbstractCustomizerModel<WarrantyFormula> {

	@EntityProperty
	private String code;

	@EntityProperty
	private String name;

	private InsuranceType insuranceType;
	private List<WarrantySelection> warrantySelections;

	public FormulaCustomizerModel() {
		this(null, null);
	}

	public FormulaCustomizerModel(final WarrantyFormula target, final InsuranceType insuranceType) {
		super(target, false);
		if (insuranceType != null) {
			warrantySelections = insuranceType.getWarranties().stream().map(WarrantySelection::new).collect(Collectors.toList());
		}
		if (target != null) {
			synchronize();
		}
	}
	
	@Override
	public void synchronize() {
		super.synchronize();
		target.getWarrantyCodes().forEach(w -> warrantySelections.stream().filter(ws -> ws.getWarranty().getCode().equals(w)).findFirst().ifPresent(ws -> ws.setSelected(true)));
	}
	
	@Override
	public void validate() {
		super.validate();
		target.getWarrantyCodes().clear();
		target.getWarrantyCodes().addAll(warrantySelections.stream().filter(WarrantySelection::isSelected).map(ws -> ws.getWarranty().getCode()).collect(Collectors.toList()));
	}

	public List<WarrantySelection> getWarrantySelections() {
		return warrantySelections;
	}
	
	static class WarrantySelection {

		private Warranty warranty;
		private boolean selected;
		
		public WarrantySelection(final Warranty warranty) {
			this.warranty = warranty;
		}

		public Warranty getWarranty() {
			return warranty;
		}

		public void setWarranty(final Warranty warranty) {
			this.warranty = warranty;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(final boolean selected) {
			this.selected = selected;
		}
		
	}
}
