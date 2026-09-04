package ngz.opengis.wcs.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

public class GetCapabilities {

    public static final String WCS_NS = "http://www.opengis.net/wcs/2.0";
    public static final String OWS_NS = "http://www.opengis.net/ows/2.0";
    public static final String INSPIRE_VS_NS = "http://inspire.ec.europa.eu/schemas/inspire_vs/1.0";
    public static final String INSPIRE_COMMON_NS = "http://inspire.ec.europa.eu/schemas/common/1.0";
    public static final String XLINK_NS = "http://www.w3.org/1999/xlink";

    @XmlRootElement(name = "Capabilities", namespace = WCS_NS)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Capabilities {

        @NotNull
        @XmlAttribute(name = "version")
        public String version;

        @Valid
        @NotNull
        @XmlElement(name = "ServiceIdentification", namespace = OWS_NS)
        public ServiceIdentification serviceIdentification;

        @Valid
        @NotNull
        @XmlElement(name = "ServiceProvider", namespace = OWS_NS)
        public ServiceProvider serviceProvider;

        @Valid
        @NotNull
        @XmlElement(name = "OperationsMetadata", namespace = OWS_NS)
        public OperationsMetadata operationsMetadata;

        @Valid
        @NotNull
        @XmlElement(name = "ServiceMetadata", namespace = WCS_NS)
        public ServiceMetadata serviceMetadata;

        @Valid
        @NotNull
        @XmlElement(name = "Contents", namespace = WCS_NS)
        public Contents contents;
    }

    // --- Service Identification ---
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServiceIdentification {
        @NotNull
        @XmlElement(name = "Title", namespace = OWS_NS)
        public String title;

        @NotNull
        @XmlElement(name = "Abstract", namespace = OWS_NS)
        public String owsAbstract;

        @NotNull
        @XmlElement(name = "ServiceType", namespace = OWS_NS)
        public String serviceType;

        @NotNull
        @XmlElement(name = "ServiceTypeVersion", namespace = OWS_NS)
        public String serviceTypeVersion;

        @XmlElement(name = "Profile", namespace = OWS_NS)
        public List<String> profiles;
    }

    // --- Service Provider ---
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServiceProvider {
        @NotNull
        @XmlElement(name = "ProviderName", namespace = OWS_NS)
        public String providerName;

        @Valid
        @NotNull
        @XmlElement(name = "ServiceContact", namespace = OWS_NS)
        public ServiceContact serviceContact;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServiceContact {
        @Valid
        @NotNull
        @XmlElement(name = "ContactInfo", namespace = OWS_NS)
        public ContactInfo contactInfo;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ContactInfo {
        @Valid
        @NotNull
        @XmlElement(name = "Address", namespace = OWS_NS)
        public Address address;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Address {
        @NotNull
        @XmlElement(name = "ElectronicMailAddress", namespace = OWS_NS)
        public String electronicMailAddress;
    }

    // --- Operations Metadata ---
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class OperationsMetadata {
        @Valid
        @NotNull
        @XmlElement(name = "Operation", namespace = OWS_NS)
        public List<Operation> operations;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Operation {
        @NotNull
        @XmlAttribute(name = "name")
        public String name;

        @Valid
        @NotNull
        @XmlElement(name = "DCP", namespace = OWS_NS)
        public Dcp dcp;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Dcp {
        @Valid
        @NotNull
        @XmlElement(name = "HTTP", namespace = OWS_NS)
        public Http http;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Http {
        @Valid
        @NotNull
        @XmlElement(name = "Get", namespace = OWS_NS)
        public GetMethod get;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class GetMethod {
        @NotNull
        @XmlAttribute(name = "href", namespace = XLINK_NS)
        public String href;
    }

    // --- Service Metadata (Extensions & Inspire) ---
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ServiceMetadata {
        @XmlElement(name = "formatSupported", namespace = WCS_NS)
        public List<String> formatsSupported;

        @Valid
        @NotNull
        @XmlElement(name = "Extension", namespace = WCS_NS)
        public Extension extension;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Extension {
        @Valid
        @NotNull
        @XmlElement(name = "ExtendedCapabilities", namespace = INSPIRE_VS_NS)
        public ExtendedCapabilities extendedCapabilities;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ExtendedCapabilities {
        @Valid
        @NotNull
        @XmlElement(name = "MetadataUrl", namespace = INSPIRE_COMMON_NS)
        public MetadataUrl metadataUrl;

        @Valid
        @NotNull
        @XmlElement(name = "SupportedLanguages", namespace = INSPIRE_COMMON_NS)
        public SupportedLanguages supportedLanguages;

        @Valid
        @NotNull
        @XmlElement(name = "ResponseLanguage", namespace = INSPIRE_COMMON_NS)
        public LanguageContainer responseLanguage;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MetadataUrl {
        @NotNull
        @XmlElement(name = "URL", namespace = INSPIRE_COMMON_NS)
        public String url;

        @NotNull
        @XmlElement(name = "MediaType", namespace = INSPIRE_COMMON_NS)
        public String mediaType;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SupportedLanguages {
        @Valid
        @NotNull
        @XmlElement(name = "DefaultLanguage", namespace = INSPIRE_COMMON_NS)
        public LanguageContainer defaultLanguage;

        @Valid
        @XmlElement(name = "SupportedLanguage", namespace = INSPIRE_COMMON_NS)
        public List<LanguageContainer> supportedLanguages;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class LanguageContainer {
        @NotNull
        @XmlElement(name = "Language", namespace = INSPIRE_COMMON_NS)
        public String language;
    }

    // --- Contents & Coverage Summaries ---
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Contents {
        @Valid
        @NotNull
        @XmlElement(name = "CoverageSummary", namespace = WCS_NS)
        public List<CoverageSummary> coverageSummaries;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CoverageSummary {
        @NotNull
        @XmlElement(name = "Title", namespace = OWS_NS)
        public String title;

        @NotNull
        @XmlElement(name = "CoverageId", namespace = WCS_NS)
        public String coverageId;

        @NotNull
        @XmlElement(name = "CoverageSubtype", namespace = WCS_NS)
        public String coverageSubtype;
    }
}
