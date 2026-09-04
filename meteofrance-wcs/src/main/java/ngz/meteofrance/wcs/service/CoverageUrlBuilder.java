package ngz.meteofrance.wcs.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service for building coverage download URLs from coverage data and dimension combinations.
 * Focuses solely on URL construction without dimension combination logic.
 */
public class CoverageUrlBuilder {

    private static final Map<String, String> BASE_QUERY_PARAMS =
            Map.of(
                    "service", "WCS",
                    "version", "2.0.1",
                    "format", "application/wmo-grib");

    /**
     * Builds a complete coverage download URL.
     *
     * @param baseUrl The base URL for the WCS service
     * @param coverageId The coverage identifier
     * @param dimensionCombination Map of dimension names to values
     * @return Complete URL string
     */
    public static String buildUrl(
            String baseUrl, String coverageId, Map<String, String> dimensionCombination) {
        String queryString = buildQueryString(coverageId, dimensionCombination);
        return baseUrl + "?" + queryString;
    }

    /**
     * Builds the query string portion of the URL.
     *
     * @param coverageId The coverage identifier
     * @param combination Map of dimension names to values
     * @return Query string
     */
    private static String buildQueryString(String coverageId, Map<String, String> combination) {
        List<String> params = new ArrayList<>();

        // Base params (service, version, format)
        BASE_QUERY_PARAMS.forEach((k, v) -> params.add(encodeParam(k, v)));

        // Coverage ID
        params.add(encodeParam("coverageid", coverageId));

        // Subset params: each dimension becomes subset=key(value)
        combination.forEach((k, v) -> params.add(encodeParam("subset", k + "(" + v + ")")));

        return String.join("&", params);
    }

    /**
     * Encodes a key-value pair for URL query parameters.
     *
     * @param key The parameter key
     * @param value The parameter value
     * @return Encoded key=value string
     */
    private static String encodeParam(String key, String value) {
        return encode(key) + "=" + encode(value);
    }

    /**
     * URL-encodes a string using UTF-8.
     *
     * @param s The string to encode
     * @return URL-encoded string
     */
    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
