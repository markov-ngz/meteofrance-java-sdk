package ngz.opengis.wcs.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import ngz.opengis.wcs.dto.GetCapabilities;
import ngz.opengis.wcs.model.Capabilities;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

public class CapabilitiesMapperTest {

    private final CapabilitiesMapper mapper = new CapabilitiesMapper();

    // create fixte

    @Test
    void shouldMapCapabilities() throws Exception {

        GetCapabilities.Capabilities dto =
                GetCapabilitiesFixtureFactory.createValidGetCapabilities();

        Capabilities capabilities = mapper.mapFromDto(dto);

        assertThat(capabilities).isNotNull();

        assertThat(capabilities.getTitle())
                .isEqualTo(
                        "Download service (WCS) of fields from the limited area French numerical weather prediction model.");

        assertThat(capabilities.getProviderName()).isEqualTo("NMC FRANCE – Météo-France");

        assertThat(capabilities.getServiceType()).isEqualTo("WCS");

        assertThat(capabilities.getServiceTypeVersion()).isEqualTo("2.0.1");

        assertThat(capabilities.getDescribeCoverageUrl())
                .isEqualTo(
                        "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-INDIEN-WCS/DescribeCoverage");
        assertThat(capabilities.getGetCoverageUrl())
                .isEqualTo(
                        "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-INDIEN-WCS/GetCoverage");

        assertThat(capabilities.getSummaries())
                .hasSize(2)
                .extracting(
                        Capabilities.CoverageSummary::getId,
                        Capabilities.CoverageSummary::getTitle,
                        Capabilities.CoverageSummary::getSubtype)
                .containsExactly(
                        Tuple.tuple(
                                "ALTITUDE_TPW__ISOTHERMAL_LEVEL_27315___2026-05-21T00.00.00Z",
                                "Altitude of dew point temperature at 273.15K",
                                "ReferenceableGridCoverage"),
                        Tuple.tuple(
                                "ALTITUDE_TPW__ISOTHERMAL_LEVEL_27315___2026-05-21T06.00.00Z",
                                "Altitude of dew point temperature at 273.15K",
                                "ReferenceableGridCoverage"));
    }
}
