package org.openmrs.module.clinicalsummary.web.controller;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + ClinicalSummaryRestController.CSA_NAMESPACE)
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
