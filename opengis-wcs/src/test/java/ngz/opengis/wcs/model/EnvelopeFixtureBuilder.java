package ngz.opengis.wcs.model;

import java.time.Instant;
import java.util.List;

public class EnvelopeFixtureBuilder {

    public static Envelope.EnvelopeBuilder baseEnvelope() {
        return Envelope.builder().srsName("http://www.opengis.net/def/crs/EPSG/0/4326");
    }

    public static Envelope.EnvelopeBuilder latLonEnvelope() {
        return baseEnvelope()
                .lowerCorner(List.of(158.5, -15.0))
                .upperCorner(List.of(159.0, -13.75))
                .axisLabels(List.of("lon", "lat"))
                .uomLabels(List.of("deg", "deg"));
    }

    public static Envelope.EnvelopeBuilder latLonTimeEnvelope() {
        return baseEnvelope()
                .lowerCorner(List.of(158.5, -15.0))
                .upperCorner(List.of(159.0, -13.75))
                .beginTime(Instant.parse("2024-01-01T00:00:00Z"))
                .endTime(Instant.parse("2024-01-01T03:00:00Z"))
                .axisLabels(List.of("lon", "lat", "time"))
                .uomLabels(List.of("deg", "deg", "s"));
    }

    public static Envelope.EnvelopeBuilder latLonHeightTimeEnvelope() {
        return baseEnvelope()
                .lowerCorner(List.of(158.5, -15.0, 20.0))
                .upperCorner(List.of(159.0, -13.75, 3000.0))
                .beginTime(Instant.parse("2024-01-01T00:00:00Z"))
                .endTime(Instant.parse("2024-01-01T03:00:00Z"))
                .axisLabels(List.of("lon", "lat", "height", "time"))
                .uomLabels(List.of("deg", "deg", "m", "s"));
    }

    public static Envelope.EnvelopeBuilder latTimeEnvelope() {
        return baseEnvelope()
                .lowerCorner(List.of(-15.0))
                .upperCorner(List.of(-13.75))
                .beginTime(Instant.parse("2024-01-01T00:00:00Z"))
                .endTime(Instant.parse("2024-01-01T03:00:00Z"))
                .axisLabels(List.of("lat", "time"))
                .uomLabels(List.of("deg", "s"));
    }
}
