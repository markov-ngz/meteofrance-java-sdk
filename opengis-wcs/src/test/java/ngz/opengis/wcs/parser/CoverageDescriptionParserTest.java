package ngz.opengis.wcs.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import ngz.opengis.wcs.dto.DescribeCoverage;
import ngz.opengis.wcs.exception.WcsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoverageDescriptionXmlParserTest {

    private CoverageDescriptionXmlParser parser;

    @BeforeEach
    void setUp() {
        parser = new CoverageDescriptionXmlParser();
    }

    // --- Unhappy path: input guards ---

    @Test
    void parse_shouldThrowValidationException_whenInputIsNull() {
        assertThatThrownBy(() -> parser.parse(null))
                .isInstanceOf(WcsException.class)
                .hasMessageContaining("null");
    }

    @Test
    void parse_shouldThrowValidationException_whenInputIsEmpty() {
        assertThatThrownBy(() -> parser.parse(new byte[0]))
                .isInstanceOf(WcsException.class)
                .hasMessageContaining("empty");
    }

    // --- Unhappy path: bad XML ---

    @Test
    void parse_shouldThrowException_whenCoefficientisMissing() throws IOException {
        byte[] xml = loadFixture("wcs/coverage/missingCoefficient.xml");

        assertThatThrownBy(() -> parser.parse(xml)).isInstanceOf(WcsException.class);
    }

    @Test
    void parse_shouldThrowException_whenAxisLabelisMissing() throws IOException {
        byte[] xml = loadFixture("wcs/coverage/missingAxisLabel.xml");

        assertThatThrownBy(() -> parser.parse(xml)).isInstanceOf(WcsException.class);
    }

    // --- Happy path ---

    @Test
    void parse_shouldReturnDto_whenXml4DIsValid() throws Exception {
        byte[] xml = loadFixture("wcs/coverage/valid4DCoverage.xml");

        DescribeCoverage.CoverageDescriptions result = parser.parse(xml);

        assertThat(result).isNotNull();
        assertThat(result.coverageDescriptions).hasSize(1);
        assertThat(result.coverageDescriptions.get(0).coverageId)
                .isEqualTo(
                        "SPECIFIC_LIQUID_WATER_CONTENT__SPECIFIC_HEIGHT_LEVEL_ABOVE_GROUND___2026-05-20T18.00.00Z");
    }

    @Test
    void parse_shouldReturnDto_whenXml3DIsValid() throws Exception {
        byte[] xml = loadFixture("wcs/coverage/valid3DCoverage.xml");

        DescribeCoverage.CoverageDescriptions result = parser.parse(xml);

        assertThat(result).isNotNull();
        assertThat(result.coverageDescriptions).hasSize(1);
        assertThat(result.coverageDescriptions.get(0).coverageId)
                .isEqualTo(
                        "SIGNIFICANT_HEIGHT_OF_COMBINED_WIND_WAVES_AND_SWELL__WATER_SURFACE___2026-05-25T00.00.00Z");
    }

    // --- Helper ---

    private byte[] loadFixture(String path) throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            assertThat(stream).as("Fixture not found on classpath: %s", path).isNotNull();
            return stream.readAllBytes();
        }
    }
}
