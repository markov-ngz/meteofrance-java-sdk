package ngz.opengis.wcs.model;

import java.util.List;
import lombok.Data;

/** Model for WCS Capabilities information. */
@Data
public class Capabilities {

    private String title;
    private String providerName;
    private String serviceType;
    private String serviceTypeVersion;
    private String getCoverageUrl;
    private String describeCoverageUrl;
    private List<CoverageSummary> summaries;

    @Data
    public class CoverageSummary {
        private String id;
        private String title;
        private String subtype;
    }
}
