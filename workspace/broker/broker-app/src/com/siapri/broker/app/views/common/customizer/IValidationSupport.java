package com.siapri.broker.app.views.common.customizer;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public interface IValidationSupport {

	public IValidator NON_EMPTY_VALIDATOR = new IValidator() {
		@Override
		public IStatus validate(final Object value) {
			if (value == null || value instanceof String && ((String) value).isEmpty()) {
				return ValidationStatus.error("This field is required");
			}
			return ValidationStatus.ok();
		}
	};
}
