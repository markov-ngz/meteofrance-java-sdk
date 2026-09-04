package ngz.opengis.wcs.model;

import java.util.List;

public class CoverageFixtureBuilder {

    /** Coverage with 4 dimensions: lon, lat, height, time. */
    public static Coverage.CoverageBuilder aCoverageWithLatLonHeightTime() {
        return Coverage.builder()
                .id("coverage-lat-lon-height-time")
                .parameterName("wind_speed")
                .description("Wind speed at multiple pressure levels over time")
                .unit("m/s")
                .nativeFormat("application/x-netcdf")
                .coverageSubtype("RectifiedGridCoverage")
                .envelope(EnvelopeFixtureBuilder.latLonHeightTimeEnvelope().build())
                .axes(
                        List.of(
                                DimensionAxisFixtureBuilder.aLonAxis().order(0).build(),
                                DimensionAxisFixtureBuilder.aLatAxis().order(1).build(),
                                DimensionAxisFixtureBuilder.aHeightAxis().order(2).build(),
                                DimensionAxisFixtureBuilder.aTimeAxis().order(3).build()))
                .gridShape(List.of(21, 6, 12, 4)); // lon x lat x height x time
    }

    /** Coverage with 3 dimensions: lon, lat, time. */
    public static Coverage.CoverageBuilder aCoverageWithLatLonTime() {
        return Coverage.builder()
                .id("coverage-lat-lon-time")
                .parameterName("temperature")
                .description("Surface temperature over time")
                .unit("K")
                .nativeFormat("application/x-netcdf")
                .coverageSubtype("RectifiedGridCoverage")
                .envelope(EnvelopeFixtureBuilder.latLonTimeEnvelope().build())
                .axes(
                        List.of(
                                DimensionAxisFixtureBuilder.aLonAxis().order(0).build(),
                                DimensionAxisFixtureBuilder.aLatAxis().order(1).build(),
                                DimensionAxisFixtureBuilder.aTimeAxis().order(2).build()))
                .gridShape(List.of(21, 6, 4)); // lon x lat x time
    }

    /** Coverage with 2 dimensions: lon, lat. */
    public static Coverage.CoverageBuilder aCoverageWithLatLon() {
        return Coverage.builder()
                .id("coverage-lat-lon")
                .parameterName("precipitation")
                .description("Accumulated precipitation")
                .unit("mm")
                .nativeFormat("application/x-netcdf")
                .coverageSubtype("RectifiedGridCoverage")
                .envelope(EnvelopeFixtureBuilder.latLonEnvelope().build())
                .axes(
                        List.of(
                                DimensionAxisFixtureBuilder.aLonAxis().order(0).build(),
                                DimensionAxisFixtureBuilder.aLatAxis().order(1).build()))
                .gridShape(List.of(21, 6)); // lon x lat
    }

    /** Coverage with 2 dimensions: lat, time (no longitude). */
    public static Coverage.CoverageBuilder aCoverageWithLatTime() {
        return Coverage.builder()
                .id("coverage-lat-time")
                .parameterName("sea_surface_temperature")
                .description("Sea surface temperature along a latitude transect over time")
                .unit("K")
                .nativeFormat("application/x-netcdf")
                .coverageSubtype("RectifiedGridCoverage")
                .envelope(EnvelopeFixtureBuilder.latTimeEnvelope().build())
                .axes(
                        List.of(
                                DimensionAxisFixtureBuilder.aLatAxis().order(0).build(),
                                DimensionAxisFixtureBuilder.aTimeAxis().order(1).build()))
                .gridShape(List.of(6, 4)); // lat x time
    }
}
