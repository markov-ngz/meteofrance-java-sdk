package ngz.opengis.wcs.model;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Envelope {

    private List<Double> lowerCorner;

    private List<Double> upperCorner;

    private Instant beginTime;

    private Instant endTime;

    private String srsName;

    private List<String> axisLabels;

    private List<String> uomLabels;
}
