package ngz.opengis.wcs.model;

import java.time.Instant;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WcsFile {

    // Basic file info
    private String fileName;
    private Map<String, String> metadata;
    private String format; // wmo-grib => .grib2
    private Instant createdAt;

    private String url; // link to download file

    // Wcs Model the coverage file belongs to
    private String model;
    private String modelType;

    // Coverage related information
    private String coverageId;
    private String coverageTitle; // title summary <=> coverage description
    private Instant coverageReferenceTime;
    private Map<String, String> dimensions; // useful to
}
