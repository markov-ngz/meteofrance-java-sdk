package ngz.opengis.wcs.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coverage {

    private String id;

    private String parameterName;

    private String description;

    private String unit;

    private Envelope envelope;

    private List<DimensionAxis> axes;

    private List<Integer> gridShape;

    private String nativeFormat;

    private String coverageSubtype;
}
