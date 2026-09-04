package ngz.opengis.wcs.mapper;

import java.util.ArrayList;
import java.util.List;
import ngz.opengis.wcs.dto.GetCapabilities;
import ngz.opengis.wcs.exception.WcsMappingException;
import ngz.opengis.wcs.model.Capabilities;

/** Interface for mapping CoverageSummary DTO objects to domain model objects. */
public class CapabilitiesMapper
        implements WcsObjectMapper<GetCapabilities.Capabilities, Capabilities> {

    /**
     * Maps a Capabilities DTO to a Capabilities model.
     *
     * @param dto the Capabilities DTO to map
     * @return the mapped Capabilities model
     */
    @Override
    public Capabilities mapFromDto(GetCapabilities.Capabilities dto) throws WcsMappingException {

        if (dto == null) {
            throw new WcsMappingException("Capabilities DTO cannot be null");
        }

        try {
            Capabilities model = new Capabilities();

            mapServiceIdentification(dto, model);
            mapServiceProvider(dto, model);
            mapHttpUrls(dto.operationsMetadata, model);

            model.setSummaries(mapCoverageSummaries(dto.contents));

            return model;

        } catch (Exception e) {
            throw new WcsMappingException("Failed to map Capabilities DTO", e);
        }
    }

    /** Maps service identification fields. */
    private void mapServiceIdentification(GetCapabilities.Capabilities dto, Capabilities model) {

        model.setTitle(dto.serviceIdentification.title);
        model.setServiceType(dto.serviceIdentification.serviceType);
        model.setServiceTypeVersion(dto.serviceIdentification.serviceTypeVersion);
    }

    /** Maps service provider fields. */
    private void mapServiceProvider(GetCapabilities.Capabilities dto, Capabilities model) {
        model.setProviderName(dto.serviceProvider.providerName);
    }

    /** Maps operations metadata. */
    private void mapHttpUrls(
            GetCapabilities.OperationsMetadata dtoOperationMetadata, Capabilities model)
            throws WcsMappingException {

        for (GetCapabilities.Operation op : dtoOperationMetadata.operations) {
            if (op.name.equals("GetCoverage")) {
                model.setGetCoverageUrl(op.dcp.http.get.href);
            } else if (op.name.equals("DescribeCoverage")) {
                model.setDescribeCoverageUrl(op.dcp.http.get.href);
            }
        }
        if (model.getGetCoverageUrl() == null || model.getDescribeCoverageUrl() == null) {
            throw new WcsMappingException("Error ...");
        }
    }

    /** Maps coverage summaries. */
    private List<Capabilities.CoverageSummary> mapCoverageSummaries(
            GetCapabilities.Contents contents) {

        List<Capabilities.CoverageSummary> summaries = new ArrayList<>();

        for (GetCapabilities.CoverageSummary dtoSummary : contents.coverageSummaries) {
            summaries.add(mapCoverageSummary(dtoSummary));
        }

        return summaries;
    }

    /** Maps a single coverage summary. */
    private Capabilities.CoverageSummary mapCoverageSummary(
            GetCapabilities.CoverageSummary dtoSummary) {

        Capabilities capabilities = new Capabilities();
        Capabilities.CoverageSummary summary = capabilities.new CoverageSummary();

        summary.setId(dtoSummary.coverageId);
        summary.setTitle(dtoSummary.title);
        summary.setSubtype(dtoSummary.coverageSubtype);

        return summary;
    }
}
