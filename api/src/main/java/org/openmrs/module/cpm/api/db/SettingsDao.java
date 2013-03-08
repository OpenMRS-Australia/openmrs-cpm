package org.openmrs.module.cpm.api.db;

import org.openmrs.module.cpm.Settings;

public interface SettingsDao {

	Settings get();

	void update(Settings settings);
}
