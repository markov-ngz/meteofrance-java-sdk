package ngz.meteofrance.wcs.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ngz.meteofrance.wcs.utils.CartesianProductUtils;
import ngz.opengis.wcs.model.Coverage;
import ngz.opengis.wcs.model.DimensionAxis;

/**
 * Service for generating combinations of fixed dimensions from coverage data. Handles the cartesian
 * product logic for non-latitude/longitude dimensions.
 */
public class DimensionCombinationGenerator {

    private static final String DIMENSION_AXIS_NAME_1 = "lat";
    private static final String DIMENSION_AXIS_NAME_2 = "long";

    /**
     * Returns all combinations of fixed-dimension values as a list of maps. Example:
     * time=[3600,7200] height=[0,500] => [ {time=3600, height=0}, {time=3600, height=500},
     * {time=7200, height=0}, {time=7200, height=500} ]
     *
     * @param coverage The coverage containing dimension axes
     * @return List of dimension combinations as maps
     */
    public static List<Map<String, String>> generateFixedDimensionsCombination(Coverage coverage) {

        List<DimensionAxis> fixedDimensions = getFixedDimensions(coverage);

        // Extract coefficients for each axis (use raw coordinates, not computed values)
        List<List<Object>> valueLists =
                fixedDimensions.stream()
                        .map(
                                axis -> {
                                    List<Object> vals = new ArrayList<>();
                                    // Use coefficients directly as the coordinate values
                                    if (!axis.getCoefficients().isEmpty()) {
                                        axis.getCoefficients().forEach(vals::add);
                                    }
                                    return vals;
                                })
                        .collect(Collectors.toList());

        // Compute cartesian product
        List<List<Object>> cartesianProduct = CartesianProductUtils.cartesianProduct(valueLists);

        // Convert to maps
        return cartesianProduct.stream()
                .map(
                        combination -> {
                            Map<String, String> axisValues = new LinkedHashMap<>();
                            for (int i = 0; i < fixedDimensions.size(); i++) {
                                axisValues.put(
                                        fixedDimensions.get(i).getName(),
                                        String.valueOf(combination.get(i)));
                            }
                            return axisValues;
                        })
                .collect(Collectors.toList());
    }

    /**
     * Gets the fixed dimensions (non-latitude/longitude) from coverage.
     *
     * @param coverage The coverage containing all axes
     * @return List of fixed dimension axes
     * @throws IllegalArgumentException if coverage doesn't contain both lat and long axes
     */
    private static List<DimensionAxis> getFixedDimensions(Coverage coverage) {

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
