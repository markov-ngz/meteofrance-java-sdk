package ngz.markov.meteofrance.wcs.tools;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ngz.opengis.wcs.model.Coverage;
import ngz.opengis.wcs.model.CoverageFixtureBuilder;
import org.junit.jupiter.api.Test;

class DownloadCoverageUrlGeneratorTest {

    @Test
    void shouldReturnCorrectUrlfor2DCoverage() {
        String baseUrl =
                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-NCALED-WCS/GetCoverage";

        Coverage coverage = CoverageFixtureBuilder.aCoverageWithLatLon().build();
        List<String> result = DownloadCoverageUrlGenerator.generate(baseUrl, coverage);

        assertThat(result).hasSize(1);

        String url = result.get(0);

        assertHasBaseUrl(url, baseUrl);
        assertQueryParams(
                url,
                Map.of(
                        "service", "WCS",
                        "version", "2.0.1",
                        "format", "application/wmo-grib", // decoded form
                        "coverageid", coverage.getId()));
    }

    @Test
    void shouldReturnCorrectUrlfor3DCoverage() {
        String baseUrl =
                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-NCALED-WCS/GetCoverage";

        Coverage coverage = CoverageFixtureBuilder.aCoverageWithLatLonTime().build();
        List<String> result = DownloadCoverageUrlGenerator.generate(baseUrl, coverage);

        assertThat(result).hasSize(4);

        String url = result.get(2);

        assertHasBaseUrl(url, baseUrl);
        assertQueryParams(
                url,
                Map.of(
                        "service", "WCS",
                        "version", "2.0.1",
                        "format", "application/wmo-grib", // decoded form
                        "coverageid", coverage.getId()));
    }

    @Test
    void shouldReturnCorrectUrlfor4DCoverage() {
        String baseUrl =
                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-NCALED-WCS/GetCoverage";

        Coverage coverage = CoverageFixtureBuilder.aCoverageWithLatLonHeightTime().build();
        List<String> result = DownloadCoverageUrlGenerator.generate(baseUrl, coverage);

        assertThat(result).hasSize(4 * 12);

        String url = result.get(2);

        assertHasBaseUrl(url, baseUrl);
        assertQueryParams(
                url,
                Map.of(
                        "service", "WCS",
                        "version", "2.0.1",
                        "format", "application/wmo-grib", // decoded form
                        "coverageid", coverage.getId()));
    }

    // --- Helpers ---

    private void assertHasBaseUrl(String url, String expectedBaseUrl) {
        String actualBase = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
        assertThat(actualBase).as("Base URL mismatch").isEqualTo(expectedBaseUrl);
    }

    private void assertQueryParams(String url, Map<String, String> expectedParams) {
        Map<String, List<String>> actualParams = parseQueryParams(url);

        expectedParams.forEach(
                (key, expectedValue) ->
                        assertThat(actualParams)
                                .as("Query param '%s' not found or incorrect", key)
                                .satisfies(
                                        params ->
                                                assertThat(params.get(key))
                                                        .as("Values for param '%s'", key)
                                                        .isNotNull()
                                                        .contains(expectedValue)));
    }

    /** Handles repeated keys (e.g. subset=time(...)&subset=height(...)) as a list */
    private Map<String, List<String>> parseQueryParams(String url) {
        Map<String, List<String>> params = new LinkedHashMap<>();

        String query = url.contains("?") ? url.substring(url.indexOf("?") + 1) : "";
        if (query.isBlank()) {
            return params;
        }

        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String key = decode(kv[0]);
            String value = kv.length > 1 ? decode(kv[1]) : "";
            params.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }
        return params;
    }

    private String decode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
}
