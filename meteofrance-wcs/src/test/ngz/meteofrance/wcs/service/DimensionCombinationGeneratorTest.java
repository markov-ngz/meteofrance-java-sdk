package ngz.markov.meteofrance.wcs.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import ngz.opengis.wcs.model.Coverage;
import ngz.opengis.wcs.model.CoverageFixtureBuilder;
import org.junit.jupiter.api.Test;

public class DimensionCombinationGeneratorTest {

    @Test
    void shouldReturn1ElementFor2DCoverage() {
        // Given: 2D coverage with only lat and lon (no fixed dimensions)
        Coverage coverage = CoverageFixtureBuilder.aCoverageWithLatLon().build();

        // When: generating fixed dimension combinations
        List<Map<String, String>> result =
                DimensionCombinationGenerator.generateFixedDimensionsCombination(coverage);

        // Then: should return exactly 1 empty combination
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEmpty();
    }

    @Test
    void shouldReturn4ElementsFor3DCoverage() {
        // Given: 3D coverage with lat, lon, and time (1 fixed dimension with 4 values)
        Coverage coverage = CoverageFixtureBuilder.aCoverageWithLatLonTime().build();

        // When: generating fixed dimension combinations
        List<Map<String, String>> result =
                DimensionCombinationGenerator.generateFixedDimensionsCombination(coverage);

        // Then: should return 4 combinations (one for each time value)
        assertThat(result).hasSize(4);

        // Verify each combination contains exactly the time dimension
        result.forEach(
                combination -> {
                    assertThat(combination).hasSize(1);
                    assertThat(combination).containsKey("time");
                });

        // Verify the time values are the coefficients (coordinates) from the fixture
        // coefficients = [0.0, 3600.0, 7200.0, 10800.0] (hourly steps)
        assertThat(result).extracting("time").containsExactly("0.0", "3600.0", "7200.0", "10800.0");
    }

    @Test
    void shouldReturn48ElementsFor4DCoverage() {
        // Given: 4D coverage with lat, lon, height, and time (2 fixed dimensions: height=12 values,
        // time=4 values)
        Coverage coverage = CoverageFixtureBuilder.aCoverageWithLatLonHeightTime().build();

        // When: generating fixed dimension combinations
        List<Map<String, String>> result =
                DimensionCombinationGenerator.generateFixedDimensionsCombination(coverage);

        // Then: should return 48 combinations (12 height values × 4 time values)
        assertThat(result).hasSize(48);

        // Verify each combination contains exactly height and time dimensions
        result.forEach(
                combination -> {
                    assertThat(combination).hasSize(2);
                    assertThat(combination).containsKeys("height", "time");
                });

        // Verify we have all combinations of height and time values
        // Height values: 20.0, 50.0, 100.0, 250.0, 500.0, 750.0, 1000.0, 1250.0, 1500.0, 2000.0,
        // 2500.0, 3000.0
        // Time values: 0.0, 3600.0, 7200.0, 10800.0

        // Check that we have the right number of unique height and time values
        // Since this is a cartesian product, each height value appears 4 times (once per time
        // value)
        // and each time value appears 12 times (once per height value)
        assertThat(result)
                .extracting("height")
                .contains(
                        "20.0", "50.0", "100.0", "250.0", "500.0", "750.0", "1000.0", "1250.0",
                        "1500.0", "2000.0", "2500.0", "3000.0")
                .hasSize(48);

        assertThat(result)
                .extracting("time")
                .contains("0.0", "3600.0", "7200.0", "10800.0")
                .hasSize(48);
    }
}
