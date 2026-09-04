package ngz.opengis.wcs.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import java.util.List;

/** Dto of the XML received by the DescribeCoverage endpoint. */
public class DescribeCoverage {

    public static final String WCS_NS = "http://www.opengis.net/wcs/2.0";
    public static final String GML_NS = "http://www.opengis.net/gml/3.2";
    public static final String GMLRGRID_NS = "http://www.opengis.net/gml/3.3/rgrid";
    public static final String GMLCOV_NS = "http://www.opengis.net/gmlcov/1.0";
    public static final String SWE_NS = "http://www.opengis.net/swe/2.0";

    @XmlRootElement(name = "CoverageDescriptions", namespace = WCS_NS)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CoverageDescriptions {

        @Valid
        @NotNull
        @XmlElement(name = "CoverageDescription", namespace = WCS_NS)
        public List<CoverageDescription> coverageDescriptions;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CoverageDescription {
        @NotNull
        @XmlAttribute(name = "id", namespace = GML_NS)
        public String id;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public BoundedBy boundedBy;

        @Valid
        @NotNull
        @XmlElement(name = "CoverageId", namespace = WCS_NS)
        public String coverageId;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public DomainSet domainSet;

        @Valid
        @NotNull
        @XmlElement(namespace = GMLCOV_NS)
        public RangeType rangeType;

        @Valid
        @NotNull
        @XmlElement(name = "ServiceParameters", namespace = WCS_NS)
        public ServiceParameters serviceParameters;
    }

    // --- GML Bounding Elements ---

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BoundedBy {
        @Valid
        @NotNull
        @XmlElement(name = "EnvelopeWithTimePeriod", namespace = GML_NS)
        public EnvelopeWithTimePeriod envelopeWithTimePeriod;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class EnvelopeWithTimePeriod {
        @NotNull @XmlAttribute public String srsName;
        @NotNull @XmlAttribute public String axisLabels;
        @NotNull @XmlAttribute public String uomLabels;
        @NotNull @XmlAttribute public Integer srsDimension;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public String lowerCorner;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public String upperCorner;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public Position beginPosition;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public Position endPosition;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position {
        @XmlAttribute public String frame;
        @NotNull @XmlValue public String value;
    }

    // --- Domain Set / Grids ---

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DomainSet {

        @Valid
        @NotNull
        @XmlElement(name = "ReferenceableGridByVectors", namespace = GMLRGRID_NS)
        public ReferenceableGridByVectors referenceableGridByVectors;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ReferenceableGridByVectors {
        @NotNull
        @XmlAttribute(name = "id", namespace = GML_NS)
        public String id;

        @NotNull @XmlAttribute public Integer dimension;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public Limits limits;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public String axisLabels;

        @Valid
        @NotNull
        @XmlElement(namespace = GMLRGRID_NS)
        public Origin origin;

        @Valid
        @NotNull
        @XmlElement(name = "generalGridAxis", namespace = GMLRGRID_NS)
        public List<GeneralGridAxisWrapper> generalGridAxes;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Limits {
        @Valid
        @NotNull
        @XmlElement(name = "GridEnvelope", namespace = GML_NS)
        public GridEnvelope gridEnvelope;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GridEnvelope {
        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public String low;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public String high;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Origin {
        @Valid
        @NotNull
        @XmlElement(name = "Point", namespace = GML_NS)
        public Point point;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Point {
        @NotNull
        @XmlAttribute(name = "id", namespace = GML_NS)
        public String id;

        @NotNull @XmlAttribute public Integer srsDimension;
        @NotNull @XmlAttribute public String srsName;

        @Valid
        @NotNull
        @XmlElement(namespace = GML_NS)
        public String pos;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GeneralGridAxisWrapper {

        @Valid
        @NotNull
        @XmlElement(name = "GeneralGridAxis", namespace = GMLRGRID_NS)
        public GeneralGridAxis generalGridAxis;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GeneralGridAxis {
        @Valid
        @NotNull
        @XmlElement(namespace = GMLRGRID_NS)
        public OffsetVector offsetVector;

        @Valid
        @XmlElement(namespace = GMLRGRID_NS)
        @NotNull(message = "Coefficient node is missing")
        public String coefficients;

        @Valid
        @NotNull
        @XmlElement(namespace = GMLRGRID_NS)
        public String gridAxesSpanned;

        @Valid
        @NotNull
        @XmlElement(namespace = GMLRGRID_NS)
        public SequenceRule sequenceRule;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OffsetVector {
        @XmlAttribute public Integer srsDimension;
        @XmlAttribute public String axisLabels;
        @XmlAttribute public String uomLabels;
        @XmlAttribute public String srsName;
        @NotNull @XmlValue public String value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SequenceRule {
        @XmlAttribute public String axisOrder;
        @NotNull @XmlValue public String value;
    }

    // --- Range Type / SWE Elements ---

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RangeType {
        @Valid
        @NotNull
        @XmlElement(name = "DataRecord", namespace = SWE_NS)
        public DataRecord dataRecord;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class DataRecord {
        @Valid
        @NotNull
        @XmlElement(namespace = SWE_NS)
        public SweField field;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SweField {
        @NotNull @XmlAttribute public String name;

        @Valid
        @NotNull
        @XmlElement(name = "Quantity", namespace = SWE_NS)
        public SweQuantity quantity;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SweQuantity {
        @XmlAttribute public String definition;

        @Valid
        @NotNull
        @XmlElement(namespace = SWE_NS)
        public String description;

        @Valid
        @NotNull
        @XmlElement(namespace = SWE_NS)
        public SweUom uom;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SweUom {
        @NotNull @XmlAttribute public String code;
    }

    // --- Service Parameters ---

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServiceParameters {
        @Valid
        @NotNull
        @XmlElement(name = "CoverageSubtype", namespace = WCS_NS)
        public String coverageSubtype;

        @Valid
        @NotNull
        @XmlElement(name = "nativeFormat", namespace = WCS_NS)
        public String nativeFormat;
    }
}
