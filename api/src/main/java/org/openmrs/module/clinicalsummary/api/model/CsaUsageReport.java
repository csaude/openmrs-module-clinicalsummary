package org.openmrs.module.clinicalsummary.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openmrs.BaseOpenmrsData;

@Entity
@Table(name = "clinicalsummary_usage_report")
public class CsaUsageReport {

	private static final long serialVersionUID = 793191237565653211L;
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name = "reporte")
	private String reporte;

	@Column(name = "unidade_sanitaria")
	private String unidadeSanitaria;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "terms")
	private String terms;

	@Column(name = "application_version")
	private String applicationVersion;

	@Column(name = "location")
	private String location;

	@Column(name = "location_uuid")
	private String locationUuid;

	public CsaUsageReport() {
	}

	public CsaUsageReport(String reporte, String unidadeSanitaria, String userName, String terms, String applicationVersion, String location, String locationUuid) {
		this.reporte = reporte;
		this.unidadeSanitaria = unidadeSanitaria;
		this.userName = userName;
		this.terms = terms;
		this.applicationVersion = applicationVersion;
		this.location = location;
		this.locationUuid = locationUuid;
	}


	public Integer getId() {
		return this.id;
	}

	
	public void setId(Integer integer) {

	}
	
	public String getReporte() {
		return reporte;
	}

	public String getUnidadeSanitaria() {
		return unidadeSanitaria;
	}

	public String getUserName() {
		return userName;
	}

	public String getTerms() {
		return terms;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public String getLocation() {
		return location;
	}

	public String getLocationUuid() {
		return locationUuid;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public void setUnidadeSanitaria(String unidadeSanitaria) {
		this.unidadeSanitaria = unidadeSanitaria;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}


}
