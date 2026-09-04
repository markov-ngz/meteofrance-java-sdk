package ngz.opengis.wcs.model;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class DimensionAxisFixtureBuilder {

    public static DimensionAxis.DimensionAxisBuilder aLonAxis() {
        return DimensionAxis.builder()
                .name("long")
                .type(DimensionAxis.AxisType.lon)
                .unit("deg")
                .coefficients(Collections.emptyList())
                .offsetVector(0.025)
                .order(0)
                .origin(158.5)
                .lowerCorner(158.5)
                .upperCorner(159.0);
    }

    public static DimensionAxis.DimensionAxisBuilder aLatAxis() {
        return DimensionAxis.builder()
                .name("lat")
                .type(DimensionAxis.AxisType.lat)
                .unit("deg")
                .coefficients(Collections.emptyList())
                .offsetVector(-0.25)
                .order(1)
                .origin(-13.75)
                .lowerCorner(-15.0)
                .upperCorner(-13.75);
    }

    public static DimensionAxis.DimensionAxisBuilder aHeightAxis() {
        return DimensionAxis.builder()
                .name("height")
                .type(DimensionAxis.AxisType.height)
                .unit("m")
                .coefficients(
                        List.of(
                                20.0, 50.0, 100.0, 250.0, 500.0, 750.0, 1000.0, 1250.0, 1500.0,
                                2000.0, 2500.0, 3000.0))
                .offsetVector(1.0)
                .order(2)
                .origin(0.0)
                .lowerCorner(20.0)
                .upperCorner(3000.0);
    }

    public static DimensionAxis.DimensionAxisBuilder aTimeAxis() {
        return DimensionAxis.builder()
                .name("time")
                .type(DimensionAxis.AxisType.time)
                .unit("s")
                .coefficients(List.of(0.0, 3600.0, 7200.0, 10800.0)) // hourly steps
                .offsetVector(1.0)
                .order(3)
                .srsName("http://www.opengis.net/def/crs/OGC/0/UnixTime")
                .origin((double) Instant.parse("2024-01-01T00:00:00Z").getEpochSecond())
                .lowerCorner((double) Instant.parse("2024-01-01T00:00:00Z").getEpochSecond())
                .upperCorner((double) Instant.parse("2024-01-01T03:00:00Z").getEpochSecond());
    }
}
