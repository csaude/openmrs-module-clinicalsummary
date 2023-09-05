package org.openmrs.module.clinicalsummary.api.task;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;


public class CsaUsageReportMigration {

    private final Log log = LogFactory.getLog(CsaUsageReportMigration.class);
    List<CsaUsageReport> usageReportList;

    private final ClinicalSummaryModuleService csaUsageReportService;

    public CsaUsageReportMigration() {
        this.csaUsageReportService = Context.getService(ClinicalSummaryModuleService.class);
    }

    public int postDataToDHISEndpoint(CsaUsageReport csaUsageReport) {
        String url = Context.getAdministrationService().getGlobalProperty("clinicalsummaryusagereportdhis.url");
        String user = Context.getAdministrationService().getGlobalProperty("clinicalsummaryusagereportdhis.user");
        String pass = Context.getAdministrationService().getGlobalProperty("clinicalsummaryusagereportdhis.pass");

        int responsecode = 0;

        String URLPath = "/api/events";
        String URL = url + URLPath;

        DefaultHttpClient client = null;

        try {
            java.net.URL dhisURL = new URL(URL);

            String host = dhisURL.getHost();
            int port = dhisURL.getPort();

            HttpHost targetHost = new HttpHost(host, port, dhisURL.getProtocol());
            client = new DefaultHttpClient();
            BasicHttpContext localcontext = new BasicHttpContext();

            HttpPost httpPost = new HttpPost(dhisURL.getPath());

            Credentials creds = new UsernamePasswordCredentials(user, pass);
            Header bs = new BasicScheme().authenticate(creds, httpPost, localcontext);

            httpPost.addHeader("Authorization", bs.getValue());
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Accept", "application/json");

   //         httpPost.setEntity(new StringEntity(csaUsageReport.getJson()));

            HttpResponse response = client.execute(targetHost, httpPost, localcontext);
            //HttpEntity entity = response.getEntity();

            responsecode = response.getStatusLine().getStatusCode();

        } catch (Exception ex) {
            log.error("Exception", ex);
        } finally {
            if (client != null)  client.getConnectionManager().shutdown();
        }

        return responsecode;
    }
    public void doMigration() throws IOException, AuthenticationException {
        log.info("Searching reports to send ...");
        this.usageReportList = this.doSearch();

        log.info("Found "+ this.usageReportList.size() + " reports...");
        if (this.usageReportList != null && this.usageReportList.size() > 0) {
            for (CsaUsageReport csaUsageReport : this.usageReportList) {
                int sent = this.postDataToDHISEndpoint(csaUsageReport);
                if (sent == 200) {
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
        //csaUsageReport.setMigrationStatus(CsaUsageReport.MIGRATED);
		
        //csaUsageReport.setDateMigrated(DateUtils.getCurrentDate());
        csaUsageReportService.save(csaUsageReport);
    }
}
