package org.openmrs.module.clinicalsummary.web.resource;

import org.openmrs.module.clinicalsummary.api.model.CsaUsageReport;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = "v1/clinicalsummary/CsaUsageReport", supportedClass = CsaUsageReport.class, supportedOpenmrsVersions = {"1.9.*","1.10.*","1.11.*","1.12.*","2.*"})
public class CsaUsageReportResource extends MetadataDelegatingCrudResource<CsaUsageReport> {
    @Override
    public CsaUsageReport getByUniqueId(String s) {
        return null;
    }

    @Override
    public CsaUsageReport newDelegate() {
        return null;
    }

    @Override
    public CsaUsageReport save(CsaUsageReport csaUsageReport) {
        return null;
    }

    @Override
    public void purge(CsaUsageReport csaUsageReport, RequestContext requestContext) throws ResponseException {

    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        return null;
    }
}
