package org.openmrs.module.clinicalsummary.api.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.scheduler.tasks.AbstractTask;

import java.io.IOException;

public class ClinicalSummaryReportProcessorTask extends AbstractTask {

    private final Log log = LogFactory.getLog(ClinicalSummaryReportProcessorTask.class);
    private final CsaUsageReportMigration csaUsageReportMigration;

    public ClinicalSummaryReportProcessorTask() {
        this.csaUsageReportMigration = new CsaUsageReportMigration();
    }
    @Override
    public void execute() {
        Context.openSession();
        try {
            this.csaUsageReportMigration.doMigration();
        } catch (IOException e) {
            log.error("Could not send reports: ",e);
            throw new RuntimeException(e);
        }
        Context.closeSession();
    }
}
