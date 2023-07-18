package org.openmrs.module.clinicalsummary.api.task;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openmrs.api.context.Context;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;
import org.openmrs.module.clinicalsummary.api.util.DateUtils;

public class CsaUsageReportMigration {

    private final Log log = LogFactory.getLog(CsaUsageReportMigration.class);
    List<CsaUsageReport> usageReportList;

    private final ClinicalSummaryModuleService csaUsageReportService;

    public CsaUsageReportMigration() {
        this.csaUsageReportService = Context.getService(ClinicalSummaryModuleService.class);
    }

    private boolean sendReport (CsaUsageReport csaUsageReport) throws IOException, AuthenticationException {
       String URLBase = "http://localhost:8099/dhis"; // 172.20.0.2//172.19.0.4:8080
       String URLPath = "/api/events";
       String URL = URLBase + URLPath;
        Boolean response = false;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        
        try {
            HttpPost httpPost = new HttpPost(URL);
            System.out.println(URL);
            String username = "admin";
            String password = "district";
            //UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
            BasicScheme scheme = new BasicScheme();
            Header authorizationHeader = scheme.authenticate(new org.apache.http.auth.UsernamePasswordCredentials(username, password), httpPost);
            httpPost.setHeader(authorizationHeader);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            
            httpPost.setEntity(new ByteArrayEntity(csaUsageReport.getJson().getBytes("UTF8")));
            
            //httpPost.setEntity((HttpEntity) csaUsageReport);
            // System.out.println("Executing request: " + httpGet.getRequestLine());
            // System.out.println(response);
            // response = httpclient.execute(httpGet,responseHandler);
            HttpResponse responseRequest = (HttpResponse) httpclient.execute(httpPost);

            if (((org.apache.http.HttpResponse) responseRequest).getStatusLine().getStatusCode() != 204
                    && ((org.apache.http.HttpResponse) responseRequest).getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException(
                        "Failed : HTTP error code : " + ((org.apache.http.HttpResponse) responseRequest).getStatusLine().getStatusCode());
            }

            httpclient.getConnectionManager().shutdown();
            response = true;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return true;
    }

    public void doMigration() throws IOException, AuthenticationException {
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
