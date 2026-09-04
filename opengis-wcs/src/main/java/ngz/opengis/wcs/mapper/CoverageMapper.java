package ngz.opengis.wcs.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ngz.opengis.wcs.dto.DescribeCoverage;
import ngz.opengis.wcs.exception.WcsMappingException;
import ngz.opengis.wcs.model.Coverage;
import ngz.opengis.wcs.model.DimensionAxis;
import ngz.opengis.wcs.model.Envelope;

/**
 * Interface for mapping DescribeCoverage.CoverageDescriptions DTO objects to Coverage domain model
 * objects.
 */
public class CoverageMapper
        implements WcsObjectMapper<DescribeCoverage.CoverageDescriptions, Coverage> {

    @Override
    public Coverage mapFromDto(DescribeCoverage.CoverageDescriptions dto)
            throws WcsMappingException {

        validateDto(dto);

        try {
            DescribeCoverage.CoverageDescription coverageDescription =
                    dto.coverageDescriptions.get(0);

            // 1. Build independent complex components
            Envelope envelope = buildEnvelope(coverageDescription);
            List<Integer> gridShape = buildGridShape(coverageDescription);

            // 2. Build dependent components (Axes depend on Envelope)
            List<DimensionAxis> axes = buildAxes(coverageDescription, envelope);

            // 3. Assemble the final Coverage object
            DescribeCoverage.SweField field = coverageDescription.rangeType.dataRecord.field;

            return Coverage.builder()
                    .id(coverageDescription.coverageId)
                    .envelope(envelope)
                    .axes(axes)
                    .gridShape(gridShape)
                    .parameterName(field.name)
                    .description(field.quantity.description)
                    .unit(field.quantity.uom.code)
                    .coverageSubtype(coverageDescription.serviceParameters.coverageSubtype)
                    .nativeFormat(coverageDescription.serviceParameters.nativeFormat)
                    .build();

        } catch (Exception e) {
            throw new WcsMappingException("Failed to map coverage description", e);
        }
    }

    /** Validates the root DTO. */
    private void validateDto(DescribeCoverage.CoverageDescriptions dto) throws WcsMappingException {
        if (dto == null) {
            throw new WcsMappingException("CoverageDescriptions DTO cannot be null");
        }
    }

    /** Builds and returns envelope information. */
    private Envelope buildEnvelope(DescribeCoverage.CoverageDescription dto) {

        DescribeCoverage.EnvelopeWithTimePeriod sourceEnvelope =
                dto.boundedBy.envelopeWithTimePeriod;

        Envelope.EnvelopeBuilder envelopeBuilder = Envelope.builder();

        envelopeBuilder.srsName(sourceEnvelope.srsName);
        envelopeBuilder.axisLabels(splitString(sourceEnvelope.axisLabels));
        envelopeBuilder.uomLabels(splitString(sourceEnvelope.uomLabels));
        envelopeBuilder.lowerCorner(parseDoubleList(sourceEnvelope.lowerCorner));
        envelopeBuilder.upperCorner(parseDoubleList(sourceEnvelope.upperCorner));

        return envelopeBuilder.build();
    }

    /** Builds and returns all dimension axes using the provided Envelope. */
    private List<DimensionAxis> buildAxes(
            DescribeCoverage.CoverageDescription dto, Envelope envelope)
            throws WcsMappingException {

        List<DimensionAxis> axes = new ArrayList<>();

        List<Double> origin =
                parseDoubleList(dto.domainSet.referenceableGridByVectors.origin.point.pos);

        for (DescribeCoverage.GeneralGridAxisWrapper axisWrapper :
                dto.domainSet.referenceableGridByVectors.generalGridAxes) {

            DimensionAxis axe = buildAxis(axisWrapper, envelope);

            if (axe.getOrder() < origin.size()) {
                axe.setOrigin(origin.get(axe.getOrder()));
            }

            axes.add(axe);
        }

        return axes;
    }

    /** Builds a single dimension axis using the provided Envelope. */
    private DimensionAxis buildAxis(
            DescribeCoverage.GeneralGridAxisWrapper axisWrapper, Envelope envelope)
            throws WcsMappingException {

        DimensionAxis.DimensionAxisBuilder axis = DimensionAxis.builder();
        DescribeCoverage.GeneralGridAxis sourceAxis = axisWrapper.generalGridAxis;

        // 1. Get Envelope information directly from the resolved object
        int orderEnvelop = envelope.getAxisLabels().indexOf(sourceAxis.gridAxesSpanned);

        if (orderEnvelop == -1) {
            throw new WcsMappingException(
                    String.format(
                            "Failed to map order of the Dimension through the Envelop for dim %s",
                            sourceAxis.gridAxesSpanned));
        }

        if (orderEnvelop < envelope.getLowerCorner().size()) {
            axis.lowerCorner(envelope.getLowerCorner().get(orderEnvelop));
        }
        if (orderEnvelop < envelope.getUpperCorner().size()) {
            axis.upperCorner(envelope.getUpperCorner().get(orderEnvelop));
        }

        int orderAxis =
                splitString(sourceAxis.offsetVector.axisLabels).indexOf(sourceAxis.gridAxesSpanned);

        if (orderAxis == -1) {
            throw new WcsMappingException(
                    String.format(
                            "Failed to extract the Dimension name of the offset vector, dim %s",
                            sourceAxis.gridAxesSpanned));
        }

        axis.name(sourceAxis.gridAxesSpanned);
        axis.order(orderAxis);
        axis.type(mapAxisType(sourceAxis.gridAxesSpanned));
        axis.offsetVector(parseDoubleList(sourceAxis.offsetVector.value).get(orderAxis));

        // Extract only the unit
        axis.unit(splitString(sourceAxis.offsetVector.uomLabels).get(orderAxis));
        axis.srsName(sourceAxis.offsetVector.srsName);
        axis.coefficients(parseDoubleList(sourceAxis.coefficients));

        return axis.build();
    }

    /** Builds and returns grid shape from grid envelope. */
    private List<Integer> buildGridShape(DescribeCoverage.CoverageDescription dto) {

        DescribeCoverage.GridEnvelope gridEnvelope =
                dto.domainSet.referenceableGridByVectors.limits.gridEnvelope;

        List<Integer> low = parseIntegerList(gridEnvelope.low);
        List<Integer> high = parseIntegerList(gridEnvelope.high);

        List<Integer> gridShape = new ArrayList<>();

        for (int i = 0; i < Math.min(low.size(), high.size()); i++) {
            gridShape.add(high.get(i) - low.get(i) + 1);
        }

        return gridShape;
    }

    /** Maps axis type from axis name. */
    private DimensionAxis.AxisType mapAxisType(String axisName) {

        if (axisName == null) {
            return null;
        }

        String normalized = axisName.toLowerCase();

        return switch (normalized) {
            case "lon", "long", "longitude", "x" -> DimensionAxis.AxisType.lon;
            case "lat", "latitude", "y" -> DimensionAxis.AxisType.lat;
            case "height", "elevation", "z" -> DimensionAxis.AxisType.height;
            case "time", "t" -> DimensionAxis.AxisType.time;
            default -> null;
        };
    }

    /** Parses a whitespace separated string into a list of strings. */
    private List<String> splitString(String value) {

        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }

        return List.of(value.trim().split("\\s+"));
    }

    /** Parses a whitespace separated string into doubles. */
    private List<Double> parseDoubleList(String value) {

        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }

        List<Double> result = new ArrayList<>();

        for (String token : value.trim().split("\\s+")) {
            result.add(Double.valueOf(token));
        }

        return result;
    }

    /** Parses a whitespace separated string into integers. */
    private List<Integer> parseIntegerList(String value) {

        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();

        for (String token : value.trim().split("\\s+")) {
            result.add(Integer.valueOf(token));
        }

        return result;
    }
}
