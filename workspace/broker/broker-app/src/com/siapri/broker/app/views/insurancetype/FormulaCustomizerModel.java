package com.siapri.broker.app.views.insurancetype;

import java.util.List;
import java.util.stream.Collectors;

import com.siapri.broker.app.views.common.customizer.AbstractCustomizerModel;
import com.siapri.broker.business.model.InsuranceType;
import com.siapri.broker.business.model.Warranty;
import com.siapri.broker.business.model.WarrantyFormula;

public class FormulaCustomizerModel extends AbstractCustomizerModel<WarrantyFormula> {
	
	private String code;
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
		code = target.getCode();
		name = target.getName();
		// insuranceType = target.getInsuranceType();
		target.getWarrantyCodes().forEach(w -> warrantySelections.stream().filter(ws -> ws.getWarranty().getCode().equals(w)).findFirst().ifPresent(ws -> ws.setSelected(true)));
	}

	@Override
	public void validate() {
		target.setCode(code);
		target.setName(name);
		// target.setInsuranceType(insuranceType);
		target.getWarrantyCodes().clear();
		target.getWarrantyCodes().addAll(warrantySelections.stream().filter(WarrantySelection::isSelected).map(ws -> ws.getWarranty().getCode()).collect(Collectors.toList()));
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(final String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(final InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public List<WarrantySelection> getWarrantySelections() {
		return warrantySelections;
	}

	public void setWarrantySelections(final List<WarrantySelection> warrantySelections) {
		this.warrantySelections = warrantySelections;
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
