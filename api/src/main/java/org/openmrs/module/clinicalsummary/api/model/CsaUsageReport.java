package org.openmrs.module.clinicalsummary.api.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Obs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "clinicalsummary_usage_report")
public class CsaUsageReport extends BaseOpenmrsMetadata {

    public static final String PENDING = "PENDING";
    public static final String MIGRATED = "MIGRATED";

    @Id
    @GeneratedValue
    private Integer id;

    private String json;

    @Column(name = "migration_status")
    private String migrationStatus;

    @Column(name = "date_migrated")
    private Date dateMigrated;

    public CsaUsageReport() {
    }

    public CsaUsageReport(String json, String migrationStatus) {
        this.json = json;
        this.migrationStatus = migrationStatus;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer integer) {

    }



    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getMigrationStatus() {
        return migrationStatus;
    }

    public void setMigrationStatus(String migrationStatus) {
        this.migrationStatus = migrationStatus;
    }

    public Date getDateMigrated() {
        return dateMigrated;
    }

    public void setDateMigrated(Date dateMigrated) {
        this.dateMigrated = dateMigrated;
    }
}
