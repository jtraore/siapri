package com.ngosdi.lawyer.app.views.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

import com.ngosdi.lawyer.app.BundleUtil;
import com.ngosdi.lawyer.services.IServiceDao;
import com.siapri.broker.business.model.Preference;

public class AppPreferenceStore implements IPreferenceStore {
	
	private final Map<String, Preference> preferences = new HashMap<>();
	private final IServiceDao serviceDao = BundleUtil.getService(IServiceDao.class);
	
	public AppPreferenceStore() {
		final List<Preference> preferenceList = serviceDao.getAll(Preference.class);
		for (final Preference preference : preferenceList) {
			preferences.put(preference.getName(), preference);
		}
		
		initDefaultPreferences();
	}
	
	private void initDefaultPreferences() {
		if (!preferences.containsKey(EPreferenceKey.AUTO_CONNECTION.name())) {
			preferences.put(EPreferenceKey.AUTO_CONNECTION.name(), new Preference(EPreferenceKey.AUTO_CONNECTION.name(), Boolean.valueOf(false).toString()));
		}
		if (!preferences.containsKey(EPreferenceKey.CURRENCY.name())) {
			preferences.put(EPreferenceKey.CURRENCY.name(), new Preference(EPreferenceKey.CURRENCY.name(), "DINAR"));
		}
		if (!preferences.containsKey(EPreferenceKey.REPORT_PATH.name())) {
			preferences.put(EPreferenceKey.REPORT_PATH.name(), new Preference(EPreferenceKey.REPORT_PATH.name(), System.getProperty("user.home")));
		}
		if (!preferences.containsKey(EPreferenceKey.REPORT_DEFAULT_PRINTING.name())) {
			preferences.put(EPreferenceKey.REPORT_DEFAULT_PRINTING.name(), new Preference(EPreferenceKey.REPORT_DEFAULT_PRINTING.name(), Boolean.valueOf(false).toString()));
		}
		if (!preferences.containsKey(EPreferenceKey.REPORT_PREVIEW.name())) {
			preferences.put(EPreferenceKey.REPORT_PREVIEW.name(), new Preference(EPreferenceKey.REPORT_PREVIEW.name(), Boolean.valueOf(false).toString()));
		}
	}
	
	@Override
	public void addPropertyChangeListener(final IPropertyChangeListener paramIPropertyChangeListener) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean contains(final String paramString) {
		return preferences.containsKey(paramString);
	}
	
	@Override
	public void firePropertyChangeEvent(final String paramString, final Object paramObject1, final Object paramObject2) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean getBoolean(final String paramString) {
		return Boolean.valueOf(preferences.get(paramString).getValue());
	}
	
	@Override
	public boolean getDefaultBoolean(final String paramString) {
		return Boolean.valueOf(preferences.get(paramString).getDefaultValue());
	}
	
	@Override
	public double getDefaultDouble(final String paramString) {
		return Double.valueOf(preferences.get(paramString).getDefaultValue());
	}
	
	@Override
	public float getDefaultFloat(final String paramString) {
		return Float.valueOf(preferences.get(paramString).getDefaultValue());
	}
	
	@Override
	public int getDefaultInt(final String paramString) {
		return Integer.valueOf(preferences.get(paramString).getDefaultValue());
	}
	
	@Override
	public long getDefaultLong(final String paramString) {
		return Long.valueOf(preferences.get(paramString).getDefaultValue());
	}
	
	@Override
	public String getDefaultString(final String paramString) {
		return preferences.get(paramString).getDefaultValue();
	}
	
	@Override
	public double getDouble(final String paramString) {
		return Double.valueOf(preferences.get(paramString).getValue());
	}
	
	@Override
	public float getFloat(final String paramString) {
		return Float.valueOf(preferences.get(paramString).getValue());
	}
	
	@Override
	public int getInt(final String paramString) {
		return Integer.valueOf(preferences.get(paramString).getValue());
	}
	
	@Override
	public long getLong(final String paramString) {
		return Long.valueOf(preferences.get(paramString).getValue());
	}
	
	@Override
	public String getString(final String paramString) {
		return preferences.get(paramString).getValue();
	}
	
	@Override
	public boolean isDefault(final String paramString) {
		final Preference preference = preferences.get(paramString);
		return preference.getValue().equals(preference.getDefaultValue());
	}
	
	@Override
	public boolean needsSaving() {
		return true;
	}
	
	@Override
	public void putValue(final String key, final String value) {
	}
	
	@Override
	public void removePropertyChangeListener(final IPropertyChangeListener paramIPropertyChangeListener) {
	}
	
	@Override
	public void setDefault(final String paramString, final double paramDouble) {
		preferences.get(paramString).setDefaultValue(String.valueOf(paramDouble));
	}
	
	@Override
	public void setDefault(final String paramString, final float paramFloat) {
		preferences.get(paramString).setDefaultValue(String.valueOf(paramFloat));
	}
	
	@Override
	public void setDefault(final String paramString, final int paramInt) {
		preferences.get(paramString).setDefaultValue(String.valueOf(paramInt));
	}
	
	@Override
	public void setDefault(final String paramString, final long paramLong) {
		preferences.get(paramString).setDefaultValue(String.valueOf(paramLong));
	}
	
	@Override
	public void setDefault(final String key, final String value) {
		preferences.get(key).setDefaultValue(String.valueOf(value));
	}
	
	@Override
	public void setDefault(final String paramString, final boolean paramBoolean) {
		preferences.get(paramString).setDefaultValue(String.valueOf(paramBoolean));
	}
	
	@Override
	public void setToDefault(final String paramString) {
		final Preference preference = preferences.get(paramString);
		preference.setValue(String.valueOf(preference.getDefaultValue()));
	}
	
	@Override
	public void setValue(final String paramString, final double paramDouble) {
		preferences.get(paramString).setValue(String.valueOf(paramDouble));
	}
	
	@Override
	public void setValue(final String paramString, final float paramFloat) {
		preferences.get(paramString).setValue(String.valueOf(paramFloat));
	}
	
	@Override
	public void setValue(final String paramString, final int paramInt) {
		preferences.get(paramString).setValue(String.valueOf(paramInt));
	}
	
	@Override
	public void setValue(final String paramString, final long paramLong) {
		preferences.get(paramString).setValue(String.valueOf(paramLong));
	}
	
	@Override
	public void setValue(final String key, final String value) {
		preferences.get(key).setValue(String.valueOf(value));
	}
	
	@Override
	public void setValue(final String paramString, final boolean paramBoolean) {
		preferences.get(paramString).setValue(String.valueOf(paramBoolean));
	}
	
	public void saveAll() {
		serviceDao.saveAll(new ArrayList<>(preferences.values()));
	}
	
	public void save(final String preferenceKey) {
		if (preferences.containsKey(preferenceKey)) {
			serviceDao.save(preferences.get(preferenceKey));
		}
	}
}
