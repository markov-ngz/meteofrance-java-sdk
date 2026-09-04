package ngz.opengis.wcs.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ngz.opengis.wcs.dto.DescribeCoverage;
import ngz.opengis.wcs.model.Coverage;
import org.junit.jupiter.api.Test;

class CoverageMapperTest {

    private final CoverageMapper mapper = new CoverageMapper();

    // create fixte

    @Test
    void shouldMapCoverageDescription() throws Exception {

        DescribeCoverage.CoverageDescriptions dto =
                DescribeCoverageFixtureFactory.create4DCoverageDescription();

        // When
        Coverage coverage = mapper.mapFromDto(dto);

        // Then
        assertThat(coverage).isNotNull();

        // Basic fields
        assertThat(coverage.getId())
                .isEqualTo(
                        "SPECIFIC_LIQUID_WATER_CONTENT__SPECIFIC_HEIGHT_LEVEL_ABOVE_GROUND___2026-05-20T18.00.00Z");

        assertThat(coverage.getParameterName())
                .isEqualTo("SPECIFIC_LIQUID_WATER_CONTENT__SPECIFIC_HEIGHT_LEVEL_ABOVE_GROUND");

        assertThat(coverage.getDescription())
                .isEqualTo("cloud liquid water content at specified height level above ground");

        assertThat(coverage.getUnit()).isEqualTo("kg/kg ");

        assertThat(coverage.getCoverageSubtype()).isEqualTo("ReferenceableGridCoverage");

        assertThat(coverage.getNativeFormat()).isEqualTo("application/wmo-grib");

        // Envelope
        assertThat(coverage.getEnvelope()).isNotNull();
        assertThat(coverage.getEnvelope().getSrsName())
                .isEqualTo("http://meteofrance.fr/def/crs/3DLongLatHeight");

        assertThat(coverage.getEnvelope().getLowerCorner()).hasSize(3);
        assertThat(coverage.getEnvelope().getUpperCorner()).hasSize(3);

        // Axes
        assertThat(coverage.getAxes()).isNotNull().hasSize(4);

        // Grid shape
        assertThat(coverage.getGridShape()).isNotNull().hasSize(4);
    }
}
