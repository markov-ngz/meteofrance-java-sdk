package ngz.opengis.wcs.model;

import java.util.Iterator;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DimensionAxis implements Iterable<Double> {

    private String name;

    private AxisType type;

    private String unit;

    private List<Double> coefficients;

    private Double offsetVector;

    private int order;

    private String srsName;

    private Double origin;

    private Double lowerCorner;

    private Double upperCorner;

    @Override
    public Iterator<Double> iterator() {
        return new DimensionAxisValueIterator(this);
    }

    public enum AxisType {
        lon,
        lat,
        height,
        time,
    }
}
