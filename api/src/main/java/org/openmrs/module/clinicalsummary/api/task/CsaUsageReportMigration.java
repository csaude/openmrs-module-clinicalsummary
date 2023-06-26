package org.openmrs.module.clinicalsummary.api.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;
import org.openmrs.module.clinicalsummary.api.util.DateUtils;

import java.io.IOException;
import java.util.List;

public class CsaUsageReportMigration {

    private final Log log = LogFactory.getLog(CsaUsageReportMigration.class);
    List<CsaUsageReport> usageReportList;

    private final ClinicalSummaryModuleService csaUsageReportService;

    public CsaUsageReportMigration() {
        this.csaUsageReportService = Context.getService(ClinicalSummaryModuleService.class);
    }

    private boolean sendReport (CsaUsageReport csaUsageReport) throws IOException {
       /* String URL = URLBase + URLPath;
        Boolean response = false;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost(URL);
            System.out.println(URL);
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            BasicScheme scheme = new BasicScheme();
            Header authorizationHeader = scheme.authenticate(credentials, httpPost);
            httpPost.setHeader(authorizationHeader);
            httpPost.setEntity(input);
            // System.out.println("Executing request: " + httpGet.getRequestLine());
            // System.out.println(response);
            // response = httpclient.execute(httpGet,responseHandler);
            HttpResponse responseRequest = httpclient.execute(httpPost);

            if (responseRequest.getStatusLine().getStatusCode() != 204
                    && responseRequest.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException(
                        "Failed : HTTP error code : " + responseRequest.getStatusLine().getStatusCode());
            }

            httpclient.getConnectionManager().shutdown();
            response = true;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }*/
        return true;
    }

    public void doMigration() throws IOException {
        log.info("Searching reports to send ...");
        this.usageReportList = this.doSearch();

        log.info("Found "+ this.usageReportList.size() + " reports...");
        if (this.usageReportList != null && this.usageReportList.size() > 0) {
            for (CsaUsageReport csaUsageReport : this.usageReportList) {
                boolean sent = this.sendReport(csaUsageReport);
                if (sent) {
                    this.updateMigrationStatus(csaUsageReport);
                }
            }
        }

        log.info("Report sending finalized ...");
        this.removeOldRecords();
    }

    private void removeOldRecords() {
    }

    private List<CsaUsageReport> doSearch() {
        return csaUsageReportService.getAllForMigration();
    }

    private void updateMigrationStatus(CsaUsageReport csaUsageReport) {
        csaUsageReport.setMigrationStatus(CsaUsageReport.MIGRATED);
        csaUsageReport.setDateMigrated(DateUtils.getCurrentDate());
        csaUsageReportService.save(csaUsageReport);
    }
}
