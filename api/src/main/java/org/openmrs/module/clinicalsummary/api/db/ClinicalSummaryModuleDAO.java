/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.clinicalsummary.api.db;

import java.util.Date;
import java.util.List;

import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;

/**
 *  Database methods for {@link ClinicalSummaryModuleService}.
 */
public interface ClinicalSummaryModuleDAO {
	
	/*
	 * Add DAO methods here
	 */

	List<CsaUsageReport> getAll();


	CsaUsageReport save(CsaUsageReport report);

	void update(CsaUsageReport report);

	List<CsaUsageReport> getByMigrationStatus(String migrationStatus);
	
	CsaUsageReport getByUuid(String uuid);
	
	List<CsaUsageReport> getByHealthFacilityAndUsernameAndIssueDateAndApplicationVersion(String healthFacility, String Username, Date startDate, Date endDate, String appVersion);
}