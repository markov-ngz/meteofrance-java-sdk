package ngz.opengis.wcs.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DimensionAxisValueIterator implements Iterator<Double> {

    private final DimensionAxis axis;
    private int index = 0;
    private static final int DEFAULT_STEP = 1;
    private Double nextValue;

    public DimensionAxisValueIterator(DimensionAxis axis) {
        this.axis = axis;
        computeNextValue(); // pre compute in advance for the hasNext to work
    }

    @Override
    public boolean hasNext() {

        // if negative coeff then , is the value superior to the lowest one ?
        if (this.axis.getOffsetVector() < 0 && this.nextValue >= this.axis.getLowerCorner()) {

            return true;
        }

        if (this.axis.getOffsetVector() > 0
                && this.nextValue <= this.axis.getUpperCorner()
                && (this.axis.getCoefficients().isEmpty()
                        || index < this.axis.getCoefficients().size())) {
            return true;
        }

        return false;
    }

    @Override
    public Double next() {

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        this.index++;
        Double valueToReturn = this.nextValue; // the former nextValue is now the current to return

        computeNextValue(); // pre compute the next to be able to check the next one

        return valueToReturn;
    }

    private void computeNextValue() {

        if (this.axis.getCoefficients().isEmpty()) {
            // no coeff => multiply by offset vector
            this.nextValue =
                    this.axis.getOrigin()
                            + (this.index) * DEFAULT_STEP * this.axis.getOffsetVector();
        } else if (index < this.axis.getCoefficients().size()) {
            // coeffs so iterate over them
            this.nextValue =
                    this.axis.getOrigin()
                            + DEFAULT_STEP
                                    * this.axis.getCoefficients().get(index)
                                    * this.axis.getOffsetVector();
        }
    }
}
