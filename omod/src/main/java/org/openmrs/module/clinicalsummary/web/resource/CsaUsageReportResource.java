package org.openmrs.module.clinicalsummary.web.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.module.clinicalsummary.api.ClinicalSummaryModuleService;
import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;
import org.openmrs.module.clinicalsummary.web.controller.ClinicalSummaryRestController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.Retrievable;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + ClinicalSummaryRestController.CSA_NAMESPACE
		+ "/csausagereport", supportedClass = CsaUsageReport.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*",
				"1.10.*", "1.11.*", "1.12.*", "2.*" })
public class CsaUsageReportResource extends DataDelegatingCrudResource<CsaUsageReport> implements Retrievable {

	@Override
	public CsaUsageReport getByUniqueId(String uuid) {
		return Context.getService(ClinicalSummaryModuleService.class).getByUuid(uuid);
	}

	@Override
	public CsaUsageReport newDelegate() {
		return new CsaUsageReport();
	}

	@Override
	public CsaUsageReport save(CsaUsageReport delegate) {
		throw new ResourceDoesNotSupportOperationException(
				"Saving Csa Usage Report using this resource is not supported.");
	}

	@Override
	public void purge(CsaUsageReport delegate, RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException(
				"Purging Csa Usage Report using this resource is not supported.");
	}

	@Override
	protected PageableResult doSearch(RequestContext context) {
		HttpServletRequest request = context.getRequest();
		String healthFacilityParameter = request.getParameter("healthFacility");
		String userNameParameter = request.getParameter("Username");
		String startDateParameter = request.getParameter("startDate");
		String endDateParameter = request.getParameter("endDate");
		String appVersionParameter = request.getParameter("appVersion");

		if (StringUtils.isAllEmpty(healthFacilityParameter, userNameParameter, appVersionParameter)
				&& (StringUtils.isEmpty(startDateParameter) && StringUtils.isEmpty(endDateParameter))) {
			throw new RuntimeException(
					"Fill at least one of the following search parameters: healthFacility, Username, appVersion. Or fill both startDate and endDate!");
		}

		List<CsaUsageReport> csaUsageReports = Context.getService(ClinicalSummaryModuleService.class)
				.getByHealthFacilityAndUsernameAndIssueDateAndApplicationVersion(healthFacilityParameter,
						userNameParameter, startDateParameter, endDateParameter, appVersionParameter);

		return new NeedsPaging<CsaUsageReport>(csaUsageReports, context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("uuid");
		description.addProperty("id");
		description.addProperty("report");
		description.addProperty("unidadeSanitaria");
		description.addProperty("userName");
		description.addProperty("terms");
		description.addProperty("applicationVersion");
		description.addProperty("dateOpened");
		description.addSelfLink();
		description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
		return description;
	}

	/**
	 * Gets a description of resource's properties which can be set on creation.
	 *
	 * @return the description
	 * @throws org.openmrs.module.webservices.rest.web.response.ResponseException
	 *
	 */
	@Override
	public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
		DelegatingResourceDescription delegatingResourceDescription = new DelegatingResourceDescription();
		delegatingResourceDescription.addRequiredProperty("id");
		delegatingResourceDescription.addRequiredProperty("uuid");
		delegatingResourceDescription.addRequiredProperty("report");
		delegatingResourceDescription.addRequiredProperty("unidadeSanitaria");
		delegatingResourceDescription.addRequiredProperty("userName");
		delegatingResourceDescription.addRequiredProperty("terms");
		delegatingResourceDescription.addRequiredProperty("applicationVersion");
		delegatingResourceDescription.addRequiredProperty("dateOpened");
		return delegatingResourceDescription;
	}

	@Override
	protected void delete(CsaUsageReport delegate, String reason, RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException(
				"Deleting Csa Usage Report using this resource is not supported.");
	}

}
