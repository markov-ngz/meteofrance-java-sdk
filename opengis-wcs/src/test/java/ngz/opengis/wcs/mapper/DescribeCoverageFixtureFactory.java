package ngz.opengis.wcs.mapper;

import java.util.List;
import ngz.opengis.wcs.dto.DescribeCoverage;

public class DescribeCoverageFixtureFactory {

    public static DescribeCoverage.CoverageDescriptions create4DCoverageDescription() {

        DescribeCoverage.CoverageDescriptions descriptions =
                new DescribeCoverage.CoverageDescriptions();

        DescribeCoverage.CoverageDescription coverageDescription =
                new DescribeCoverage.CoverageDescription();

        // --------------------------------------------------------------------
        // Basic fields
        // --------------------------------------------------------------------
        coverageDescription.id =
                "SPECIFIC_LIQUID_WATER_CONTENT__SPECIFIC_HEIGHT_LEVEL_ABOVE_GROUND___2026-05-20T18.00.00Z";

        coverageDescription.coverageId =
                "SPECIFIC_LIQUID_WATER_CONTENT__SPECIFIC_HEIGHT_LEVEL_ABOVE_GROUND___2026-05-20T18.00.00Z";

        // --------------------------------------------------------------------
        // Envelope
        // --------------------------------------------------------------------
        DescribeCoverage.EnvelopeWithTimePeriod envelope =
                new DescribeCoverage.EnvelopeWithTimePeriod();

        envelope.srsName = "http://meteofrance.fr/def/crs/3DLongLatHeight";

        envelope.axisLabels = "long lat height time";
        envelope.uomLabels = "deg deg m ISO8601";
        envelope.srsDimension = 3;

        envelope.lowerCorner = "158.5 -26.00 20";
        envelope.upperCorner = "171.5 -13.75 3000";

        DescribeCoverage.Position begin = new DescribeCoverage.Position();
        begin.frame = "#ISO-8601";
        begin.value = "2026-05-20T18:00:00Z";

        DescribeCoverage.Position end = new DescribeCoverage.Position();
        end.frame = "#ISO-8601";
        end.value = "2026-05-22T18:00:00Z";

        envelope.beginPosition = begin;
        envelope.endPosition = end;

        DescribeCoverage.BoundedBy boundedBy = new DescribeCoverage.BoundedBy();

        boundedBy.envelopeWithTimePeriod = envelope;

        coverageDescription.boundedBy = boundedBy;

        // --------------------------------------------------------------------
        // Domain set
        // --------------------------------------------------------------------
        DescribeCoverage.ReferenceableGridByVectors grid =
                new DescribeCoverage.ReferenceableGridByVectors();

        grid.id = "grid_1";
        grid.dimension = 4;
        grid.axisLabels = "long lat height time";

        DescribeCoverage.GridEnvelope gridEnvelope = new DescribeCoverage.GridEnvelope();

        gridEnvelope.low = "1 1 1 1";
        gridEnvelope.high = "1395 899 6 49";

        DescribeCoverage.Limits limits = new DescribeCoverage.Limits();

        limits.gridEnvelope = gridEnvelope;

        grid.limits = limits;

        DescribeCoverage.Point point = new DescribeCoverage.Point();

        point.id = "origin_1";
        point.srsDimension = 4;
        point.srsName = "http://meteofrance.fr/def/crs/4DLongLatHeightTime";

        point.pos = "158.5 -13.75 0";

        DescribeCoverage.Origin origin = new DescribeCoverage.Origin();

        origin.point = point;

        grid.origin = origin;

        // --------------------------------------------------------------------
        // Axes
        // --------------------------------------------------------------------
        grid.generalGridAxes =
                List.of(
                        createAxis(
                                "long",
                                "0.025 0.0 0.0 0.0",
                                "",
                                "deg deg m s",
                                "http://meteofrance.fr/def/crs/4DLongLatHeightTime"),
                        createAxis(
                                "lat",
                                "0.0 -0.025 0.0 0.0",
                                "",
                                "deg deg m s",
                                "http://meteofrance.fr/def/crs/4DLongLatHeightTime"),
                        createAxis(
                                "height",
                                "0.0 0.0 1.0 0.0",
                                "20 50 100 250 500",
                                "deg deg m s",
                                "http://meteofrance.fr/def/crs/4DLatLongHeightTime"),
                        createAxis(
                                "time",
                                "0.0 0.0 0.0 1.0",
                                "0 3600 7200",
                                "deg deg m s",
                                "http://meteofrance.fr/def/crs/4DLongLatHeightTime"));

        DescribeCoverage.DomainSet domainSet = new DescribeCoverage.DomainSet();

        domainSet.referenceableGridByVectors = grid;

        coverageDescription.domainSet = domainSet;

        // --------------------------------------------------------------------
        // Range type
        // --------------------------------------------------------------------
        DescribeCoverage.SweUom uom = new DescribeCoverage.SweUom();

        uom.code = "kg/kg ";

        DescribeCoverage.SweQuantity quantity = new DescribeCoverage.SweQuantity();

        quantity.definition = "http://codes.wmo.int/grib2/codeflag/4.2/0-1-83";

        quantity.description = "cloud liquid water content at specified height level above ground";

        quantity.uom = uom;

        DescribeCoverage.SweField field = new DescribeCoverage.SweField();

        field.name = "SPECIFIC_LIQUID_WATER_CONTENT__SPECIFIC_HEIGHT_LEVEL_ABOVE_GROUND";

        field.quantity = quantity;

        DescribeCoverage.DataRecord dataRecord = new DescribeCoverage.DataRecord();

        dataRecord.field = field;

        DescribeCoverage.RangeType rangeType = new DescribeCoverage.RangeType();

        rangeType.dataRecord = dataRecord;

        coverageDescription.rangeType = rangeType;

        // --------------------------------------------------------------------
        // Service parameters
        // --------------------------------------------------------------------
        DescribeCoverage.ServiceParameters serviceParameters =
                new DescribeCoverage.ServiceParameters();

        serviceParameters.coverageSubtype = "ReferenceableGridCoverage";

        serviceParameters.nativeFormat = "application/wmo-grib";

        coverageDescription.serviceParameters = serviceParameters;

        descriptions.coverageDescriptions = List.of(coverageDescription);

        return descriptions;
    }

    private static DescribeCoverage.GeneralGridAxisWrapper createAxis(
            String axisName,
            String offsetVectorValue,
            String coefficients,
            String uomLabels,
            String srsName) {

        DescribeCoverage.OffsetVector offsetVector = new DescribeCoverage.OffsetVector();

        offsetVector.srsDimension = 4;
        offsetVector.axisLabels = "long lat height time";
        offsetVector.uomLabels = uomLabels;
        offsetVector.srsName = srsName;
        offsetVector.value = offsetVectorValue;

        DescribeCoverage.SequenceRule sequenceRule = new DescribeCoverage.SequenceRule();

        sequenceRule.axisOrder = "+1";
        sequenceRule.value = "Linear";

        DescribeCoverage.GeneralGridAxis axis = new DescribeCoverage.GeneralGridAxis();

        axis.offsetVector = offsetVector;
        axis.coefficients = coefficients;
        axis.gridAxesSpanned = axisName;
        axis.sequenceRule = sequenceRule;

        DescribeCoverage.GeneralGridAxisWrapper wrapper =
                new DescribeCoverage.GeneralGridAxisWrapper();

        wrapper.generalGridAxis = axis;

        return wrapper;
    }
}
