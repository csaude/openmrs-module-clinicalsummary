package org.openmrs.module.clinicalsummary;

import org.openmrs.api.context.Context;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController(value = "/module/clinicalsummary")
public class ClinicalSummaryReportController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public void saveReport(final @RequestBody CsaUsageReport csaUsageReport) {

        if (Context.isAuthenticated()) {
            ClinicalSummaryModuleService service = Context.getService(ClinicalSummaryModuleService.class);
            service.save(csaUsageReport);

        }
    }


    @ResponseBody
    @RequestMapping(value = "/usageReports", method = RequestMethod.GET)
    public List<CsaUsageReport> saveReport() {
        ClinicalSummaryModuleService service = Context.getService(ClinicalSummaryModuleService.class);
        return service.getAll();
    }
}
