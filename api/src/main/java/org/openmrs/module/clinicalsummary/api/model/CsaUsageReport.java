package org.openmrs.module.clinicalsummary.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openmrs.BaseChangeableOpenmrsData;

@Entity
@Table(name = "clinicalsummary_usage_report")
public class CsaUsageReport extends BaseChangeableOpenmrsData {

	private static final long serialVersionUID = 793191237565653211L;
	public static final String PENDING = "PENDING";
	public static final String MIGRATED = "MIGRATED";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "report")
	private String report;

	@Column(name = "health_facility")
	private String unidadeSanitaria;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "confidential_terms")
	private String terms;

	@Column(name = "app_version")
	private String applicationVersion;

	@Column(name = "date_opened")
	private Date dateOpened;

	public CsaUsageReport() {
		super();
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String reporte) {
		this.report = reporte;
	}

	public String getUnidadeSanitaria() {
		return unidadeSanitaria;
	}

	public void setUnidadeSanitaria(String unidadeSanitaria) {
		this.unidadeSanitaria = unidadeSanitaria;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public Date getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}
}
