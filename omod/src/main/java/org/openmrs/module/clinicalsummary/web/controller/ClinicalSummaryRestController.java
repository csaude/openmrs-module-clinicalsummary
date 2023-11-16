package org.openmrs.module.clinicalsummary.web.controller;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + ClinicalSummaryRestController.CSA_NAMESPACE)
public class ClinicalSummaryRestController extends MainResourceController {
	
	public static final String CSA_NAMESPACE = "/clinicalsummary";

	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController#getNamespace()
	 */
	@Override
	public String getNamespace() {
		return RestConstants.VERSION_1 + CSA_NAMESPACE;
	}

}
