package ngz.opengis.wcs.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import ngz.opengis.wcs.dto.GetCapabilities;
import ngz.opengis.wcs.exception.WcsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetCapabilitiesXmlParserTest {

    private GetCapabilitiesXmlParser parser;

    @BeforeEach
    void setUp() {
        parser = new GetCapabilitiesXmlParser();
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
    void parseShouldThrowExceptionWhenTitleIsMissing() throws IOException {
        byte[] xml = loadFixture("wcs/capabilities/missingCoverageSummaryTitle.xml");

        assertThatThrownBy(() -> parser.parse(xml)).isInstanceOf(WcsException.class);
    }

    @Test
    void parseShouldThrowExceptionWhenHttpIsMissing() throws IOException {
        byte[] xml = loadFixture("wcs/capabilities/missingGetCoverageHTTP.xml");

        assertThatThrownBy(() -> parser.parse(xml)).isInstanceOf(WcsException.class);
    }

    // --- Happy path ---

    @Test
    void parse_shouldReturnDto_whenXmlAromeIsValid() throws Exception {
        byte[] xml = loadFixture("wcs/capabilities/validAromeCapabilities.xml");

        GetCapabilities.Capabilities result = parser.parse(xml);

        assertThat(result).isNotNull();
        assertThat(result.contents.coverageSummaries).hasSize(6);
        assertThat(result.contents.coverageSummaries.get(0).coverageId)
                .isEqualTo("ALTITUDE_TPW__ISOTHERMAL_LEVEL_27315___2026-05-21T00.00.00Z");
    }

    @Test
    void parse_shouldReturnDto_whenXml3DIsValid() throws Exception {
        byte[] xml = loadFixture("wcs/capabilities/validWaveCapabilities.xml");

        GetCapabilities.Capabilities result = parser.parse(xml);

        assertThat(result).isNotNull();
        assertThat(result.contents.coverageSummaries).hasSizeGreaterThan(1);
        assertThat(result.contents.coverageSummaries.get(0).coverageId)
                .isEqualTo(
                        "MEAN_DIRECTION_OF_THE_PRIMARY_SWELL__WATER_SURFACE___2026-05-21T00.00.00Z");
    }

    // --- Helper ---

    private byte[] loadFixture(String path) throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(path)) {
            assertThat(stream).as("Fixture not found on classpath: %s", path).isNotNull();
            return stream.readAllBytes();
        }
    }
}
