package ngz.meteofrance.wcs.factory;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ngz.meteofrance.wcs.model.MeteoFranceCoverageFile;
import ngz.meteofrance.wcs.service.CoverageUrlBuilder;
import ngz.meteofrance.wcs.service.DimensionCombinationGenerator;
import ngz.opengis.wcs.model.Coverage;

/**
 * Factory for creating MeteoFranceCoverageFile objects from coverage data. Coordinates dimension
 * combination and URL building to create complete file objects.
 */
public class MeteoFranceCoverageFileFactory {

    /**
     * Creates MeteoFranceCoverageFile objects from coverage data.
     *
     * @param coverage The coverage data
     * @param capabilities The WCS capabilities
     * @param model The model name
     * @param modelType The model type
     * @param referenceTime The reference time for the coverage
     * @param coverageTitle The coverage title
     * @return List of MeteoFranceCoverageFile objects
     */
    public List<MeteoFranceCoverageFile> createFiles(
            Coverage coverage,
            String capabilitiesGetCoverageUrl,
            String model,
            String modelType,
            Instant referenceTime,
            String coverageTitle) {
        // Get all dimension combinations
        List<Map<String, String>> dimensionCombinations =
                DimensionCombinationGenerator.generateFixedDimensionsCombination(coverage);

        // Build URLs and create files
        return dimensionCombinations.stream()
                .map(
                        combination -> {
                            String url =
                                    CoverageUrlBuilder.buildUrl(
                                            capabilitiesGetCoverageUrl,
                                            coverage.getId(),
                                            combination);

                            return MeteoFranceCoverageFile.builder()
                                    .model(model)
                                    .modelType(modelType)
                                    .coverageId(coverage.getId())
                                    .coverageReferenceTime(referenceTime)
                                    .coverageTitle(coverageTitle)
                                    .url(url)
                                    .dimensions(extractDimensions(combination))
                                    .format(inferFormat(coverage.getNativeFormat()))
                                    .build();
                        })
                .collect(Collectors.toList());
    }

    private String inferFormat(String nativeFormat) {
        return switch (nativeFormat) {
            case "application/wmo-grib" -> ".grib2";
            default -> nativeFormat;
        };
    }

    /**
     * Extracts dimensions from a dimension combination map.
     *
     * @param combination Map of dimension names to values
     * @return Map of dimensions for the file
     */
    private Map<String, String> extractDimensions(Map<String, String> combination) {
        // Create a copy to avoid modifying the original map
        return combination.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
