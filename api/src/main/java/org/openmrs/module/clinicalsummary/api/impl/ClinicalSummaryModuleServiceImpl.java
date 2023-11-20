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
package org.openmrs.module.clinicalsummary.api.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.db.ClinicalSummaryModuleDAO;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;

/**
 * It is a default implementation of {@link ClinicalSummaryModuleService}.
 */
public class ClinicalSummaryModuleServiceImpl extends BaseOpenmrsService implements ClinicalSummaryModuleService {

	protected final Log log = LogFactory.getLog(this.getClass());

	private ClinicalSummaryModuleDAO dao;

	/**
	 * @param dao the dao to set
	 */
	public void setDao(ClinicalSummaryModuleDAO dao) {
		this.dao = dao;
	}

	/**
	 * @return the dao
	 */
	public ClinicalSummaryModuleDAO getDao() {
		return dao;
	}

	@Override
	public CsaUsageReport save(CsaUsageReport usageReport) {
		if (usageReport.getId() == null) {
			return this.dao.save(usageReport);
		} else {
			this.dao.update(usageReport);
			return usageReport;
		}
	}

	@Override
	public List<CsaUsageReport> getAll() {
		return this.dao.getAll();
	}

	@Override
	public List<CsaUsageReport> getAllForMigration() {
		return this.dao.getByMigrationStatus(CsaUsageReport.PENDING);
	}

	@Override
	public CsaUsageReport getByUuid(String uuid) {
		return dao.getByUuid(uuid);
	}

	@Override
	public List<CsaUsageReport> getByHealthFacilityAndUsernameAndIssueDateAndApplicationVersion(String healthFacility,
			String Username, String startDate, String endDate, String appVersion) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date sDate = null;
		Date eDate = null;
		if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
			try {
				sDate = dateFormat.parse(startDate);
				sDate.setTime(0);
				eDate = this.add23Hours(dateFormat.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return dao.getByHealthFacilityAndUsernameAndIssueDateAndApplicationVersion(healthFacility, Username, sDate,
				eDate, appVersion);
	}

	public Date add23Hours(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 23);
		calendar.add(Calendar.MINUTE, 59);
		calendar.add(Calendar.SECOND, 59);
		return calendar.getTime();
	}
}