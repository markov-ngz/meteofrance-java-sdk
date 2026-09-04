package ngz.meteofrance.wcs.model;

import java.time.Instant;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MeteoFranceCoverageFile {

    private String model;
    private String modelType;

    private String coverageTitle; // title summary <=> coverage description
    private String coverageId;
    private Instant coverageReferenceTime;

    private String url; // link to download file

    private Map<String, String> dimensions;
    private String format; // wmo-grib => .grib2
}
