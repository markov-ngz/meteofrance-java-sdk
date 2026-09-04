package ngz.meteofrance.wcs.tools;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ngz.meteofrance.wcs.utils.CartesianProductUtils;
import ngz.opengis.wcs.model.Coverage;
import ngz.opengis.wcs.model.DimensionAxis;

/** Generate the Url from a Coverage. */
public class DownloadCoverageUrlGenerator {

    private static final String DIMENSION_AXIS_NAME_1 = "lat";
    private static final String DIMENSION_AXIS_NAME_2 = "long";

    private static final Map<String, String> BASE_QUERY_PARAMS =
            Map.of(
                    "service", "WCS",
                    "version", "2.0.1",
                    "format", "application/wmo-grib");

    public static List<String> generate(String getCoverageUrl, Coverage coverage) {
        // cartesian product of fixed dimensions to have all coverage possible
        List<Map<String, String>> fixedDimensionsCombinations =
                generateFixedDimensionsCombination(coverage);

        return fixedDimensionsCombinations.stream()
                .map(combination -> buildUrl(getCoverageUrl, coverage.getId(), combination))
                .collect(Collectors.toList());
    }

    private static String buildUrl(
            String baseUrl, String coverageId, Map<String, String> combination) {
        String queryString = buildQueryString(coverageId, combination);
        return baseUrl + "?" + queryString;
    }

    private static String buildQueryString(String coverageId, Map<String, String> combination) {
        List<String> params = new ArrayList<>();

        // 1. Base params (service, version, format)
        BASE_QUERY_PARAMS.forEach((k, v) -> params.add(encodeParam(k, v)));

        // 2. Coverage ID
        params.add(encodeParam("coverageid", coverageId));

        // 3. Subset params: each dimension becomes subset=key(value)
        combination.forEach((k, v) -> params.add(encodeParam("subset", k + "(" + v + ")")));

        return String.join("&", params);
    }

    private static String encodeParam(String key, String value) {
        return encode(key) + "=" + encode(value);
    }

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    /**
     * Returns all combinations of fixed-dimension values as a list of maps. e.g. axes
     * time=[3600,7200] height=[0,500] => [ {time=3600, height=0}, {time=3600, height=500},
     * {time=7200, height=0}, {time=7200, height=500} ]
     */
    public static List<Map<String, String>> generateFixedDimensionsCombination(Coverage coverage) {

        // 1. Get dimensions that are NOT lat/long
        List<DimensionAxis> fixedDimensions = getDimensions(coverage);

        // 2. Extract the list of possible values per axis
        List<List<Object>> valueLists =
                fixedDimensions.stream()
                        .map(
                                axis -> {
                                    List<Object> vals = new ArrayList<>();
                                    axis.iterator().forEachRemaining(vals::add);
                                    return vals;
                                })
                        .collect(Collectors.toList());

        // 3. Compute the cartesian product
        List<List<Object>> cartesianProduct = CartesianProductUtils.cartesianProduct(valueLists);

        // 4. For each combination, build one map: axisName -> value
        //    ⚠ The map must be created OUTSIDE the inner loop (one map per combination)
        List<Map<String, String>> list = new ArrayList<>();

        for (List<Object> combination : cartesianProduct) {
            Map<String, String> axisValues =
                    new LinkedHashMap<>(); // LinkedHashMap preserves insertion order
            for (int i = 0; i < fixedDimensions.size(); i++) {
                axisValues.put(
                        fixedDimensions.get(i).getName(), String.valueOf(combination.get(i)));
            }
            list.add(axisValues);
        }

        return list;
    }

    private static List<DimensionAxis> getDimensions(Coverage coverage) {
        List<DimensionAxis> axes = coverage.getAxes();

        boolean hasLat = axes.stream().anyMatch(a -> a.getName().equals(DIMENSION_AXIS_NAME_1));
        boolean hasLong = axes.stream().anyMatch(a -> a.getName().equals(DIMENSION_AXIS_NAME_2));

        if (!hasLat || !hasLong) {
            throw new IllegalArgumentException(
                    "Coverage must contain both '"
                            + DIMENSION_AXIS_NAME_1
                            + "' and '"
                            + DIMENSION_AXIS_NAME_2
                            + "' axes.");
        }

        return axes.stream()
                .filter(
                        a ->
                                !a.getName().equals(DIMENSION_AXIS_NAME_1)
                                        && !a.getName().equals(DIMENSION_AXIS_NAME_2))
                .collect(Collectors.toList());
    }
}
