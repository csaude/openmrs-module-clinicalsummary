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
package org.openmrs.module.clinicalsummary.api.db.hibernate;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.module.clinicalsummary.api.db.ClinicalSummaryModuleDAO;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;

/**
 * It is a default implementation of {@link ClinicalSummaryModuleDAO}.
 */
public class HibernateClinicalSummaryModuleDAO implements ClinicalSummaryModuleDAO {
	protected final Log log = LogFactory.getLog(this.getClass());

	private SessionFactory sessionFactory;

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private org.hibernate.Session getCurrentSession() {
		try {
			return this.sessionFactory.getCurrentSession();
		} catch (final NoSuchMethodError ex) {
			try {
				final Method method = this.sessionFactory.getClass().getMethod("getCurrentSession", null);
				return (org.hibernate.Session) method.invoke(this.sessionFactory, null);
			} catch (final Exception e) {
				throw new RuntimeException("Failed to get the current hibernate session", e);
			}
		}
	}

	@Override
	public List<CsaUsageReport> getAll() {
		final String hql = "SELECT  l FROM CsaUsageReport l WHERE l.voided = 0";
		final Query query = this.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Override
	public CsaUsageReport save(CsaUsageReport report) {
		this.getCurrentSession().save(report);
		return report;
	}

	@Override
	public void update(CsaUsageReport report) {
		this.getCurrentSession().update(report);
	}

	@Override
	public List<CsaUsageReport> getByMigrationStatus(String migrationStatus) {
		final String hql = "SELECT  l FROM CsaUsageReport l WHERE l.migrationStatus = :migrationStatus AND l.voided = 0";
		final Query query = this.getCurrentSession().createQuery(hql).setParameter("migrationStatus", migrationStatus);
		return query.list();
	}

	@Override
	public CsaUsageReport getByUuid(String uuid) {
		final String hql = "SELECT  l FROM CsaUsageReport l WHERE l.uuid = :uuid AND l.voided = 0";
		final Query query = this.getCurrentSession().createQuery(hql).setParameter("uuid", uuid);
		List list = query.list();
		return (CsaUsageReport) list.get(0);
	}

	@Override
	public List<CsaUsageReport> getByHealthFacilityAndUsernameAndIssueDateAndApplicationVersion(String healthFacility,
			String Username, Date startDate, Date endDate, String appVersion) {
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT  l FROM CsaUsageReport l ");
		if (StringUtils.isBlank(healthFacility) && StringUtils.isBlank(Username) && StringUtils.isBlank(appVersion)
				&& startDate == null && endDate == null) {
			throw new RuntimeException(
					"Fill at least one of the following search parameters: healthFacility, Username, appVersion. Or fill both startDate and endDate!");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (!StringUtils.isBlank(healthFacility) || !StringUtils.isBlank(Username) || !StringUtils.isBlank(appVersion)
				|| startDate != null || endDate != null) {
			builder.append(" WHERE ");
			if (!StringUtils.isBlank(healthFacility)) {
				builder.append(
						!builder.toString().contains(":healthFacility") ? ((builder.toString().contains(":Username")
								|| builder.toString().contains(":startDate") || builder.toString().contains(":endDate")
								|| builder.toString().contains(":appVersion"))
										? " AND l.unidadeSanitaria = :healthFacility "
										: " l.unidadeSanitaria = :healthFacility  ")
								: "");
				parameters.put("healthFacility", healthFacility);
			}
			if (!StringUtils.isBlank(Username)) {
				builder.append(
						!builder.toString().contains(":Username") ? ((builder.toString().contains(":healthFacility")
								|| builder.toString().contains(":startDate") || builder.toString().contains(":endDate")
								|| builder.toString().contains(":appVersion")) ? " AND l.userName = :Username  "
										: " l.userName = :Username  ")
								: "");
				parameters.put("Username", Username);
			}
			if (startDate != null && endDate != null) {
				builder.append(!builder.toString().contains(":startDate") && !builder.toString().contains(":endDate")
						? ((builder.toString().contains(":Username") || builder.toString().contains(":healthFacility")
								|| builder.toString().contains(":appVersion"))
										? " AND l.dateOpened BETWEEN :startDate AND :endDate "
										: " l.dateOpened BETWEEN :startDate AND :endDate ")
						: "");
				parameters.put("startDate", startDate);
				parameters.put("endDate", endDate);
			}
			if (!StringUtils.isBlank(appVersion)) {
				builder.append(!builder.toString().contains(":appVersion") ? ((builder.toString().contains(":Username")
						|| builder.toString().contains(":startDate") || builder.toString().contains(":endDate")
						|| builder.toString().contains(":healthFacility")) ? " AND l.applicationVersion = :appVersion  "
								: " l.applicationVersion = :appVersion  ")
						: "");
				parameters.put("appVersion", appVersion);
			}
		}

		final Query query = this.getCurrentSession().createQuery(builder.toString());

		Set<String> keys = parameters.keySet();
		for (String key : keys) {
			if ("startDate".equalsIgnoreCase(key) || "endDate".equalsIgnoreCase(key)) {
				Date value = (Date) parameters.get(key);
				query.setParameter(key, value);
			} else {
				String value = (String) parameters.get(key);
				query.setParameter(key, value);
			}
		}

		return query.list();
	}
}