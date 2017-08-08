package com.ngosdi.lawyer.app.views.common.datalist;

import java.util.regex.PatternSyntaxException;

import org.apache.commons.jxpath.JXPathContext;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class DataListFilter extends ViewerFilter {

	private final String[] xPathExpressions;
	private String pattern;

	public DataListFilter(final String[] xPathExpressions) {
		this.xPathExpressions = xPathExpressions;
		pattern = ".*";
	}

	public void setPattern(final String pattern) {
		this.pattern = ".*" + pattern + ".*";
	}

	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (xPathExpressions == null) {
			return true;
		}
		final JXPathContext context = JXPathContext.newContext(element);
		context.setLenient(true);
		for (final String xPathExpression : xPathExpressions) {
			final Object value = context.getValue(xPathExpression);
			try {
				if (value != null && String.valueOf(value).toLowerCase().matches(pattern.toLowerCase())) {
					return true;
				}
			} catch (final PatternSyntaxException e) {
				// Nothing to do
			}
		}
		return false;
	}
}
