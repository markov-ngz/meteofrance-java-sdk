package ngz.opengis.wcs.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DimensionAxisValueIteratorTest {

    @Test
    void shouldIterateWithPositiveOffsetVector() {
        // Given
        DimensionAxis axis = DimensionAxisFixtureBuilder.aLonAxis().build();

        // When
        List<Double> values = new ArrayList<>();
        for (Double value : axis) {
            values.add(value);
        }

        // Then
        assertThat(values).isNotEmpty();

        // Rule: The first value must be equal to lowerCorner
        assertThat(values.get(0))
                .as("First value should match the lowerCorner")
                .isEqualTo(axis.getLowerCorner());

        // Rule: The max value must be equal to upperCorner
        assertThat(Collections.max(values))
                .as("Max value should match the upperCorner")
                .isEqualTo(axis.getUpperCorner());
    }

    @Test
    void shouldIterateWithNegativeOffsetVector() {
        // Given
        DimensionAxis axis = DimensionAxisFixtureBuilder.aLatAxis().build();

        // When
        List<Double> values = new ArrayList<>();
        for (Double value : axis) {
            values.add(value);
        }

        // Then
        assertThat(values).isNotEmpty();

        // expected values
        assertThat(values.size()).isEqualTo(6);

        // Rule: The first value must be equal to upperCorner as coeff is negative
        assertThat(values.get(0))
                .as("First value should match the upperCorner ")
                .isEqualTo(axis.getUpperCorner());

        // first value should be the max as the vector is negative
        assertThat(Collections.max(values))
                .as("First value should match the upperCorner ")
                .isEqualTo(axis.getUpperCorner());

        // Rule: The max value must be equal to upperCorner
        assertThat(Collections.min(values)).as("Min").isEqualTo(axis.getLowerCorner());
    }

    @Test
    void shouldIterateWithPositiveCoefficients() {
        // Given
        DimensionAxis axis = DimensionAxisFixtureBuilder.aHeightAxis().build();

        // When
        List<Double> values = new ArrayList<>();

        for (Double value : axis) {
            values.add(value);
        }

        // Then
        assertThat(values).isNotEmpty();

        // Specific test for coefficients: the size should match the provided coefficients
        assertThat(values).hasSize(axis.getCoefficients().size());

        // Rule: The first value must be equal to lowerCorner
        assertThat(values.get(0))
                .as("First value should match the lowerCorner")
                .isEqualTo(axis.getLowerCorner());

        // Rule: The max value must be equal to upperCorner
        assertThat(Collections.max(values))
                .as("Max value should match the upperCorner")
                .isEqualTo(axis.getUpperCorner());
    }
}
