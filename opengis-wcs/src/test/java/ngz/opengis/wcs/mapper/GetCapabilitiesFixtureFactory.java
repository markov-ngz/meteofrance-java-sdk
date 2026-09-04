package ngz.opengis.wcs.mapper;

import java.util.List;
import ngz.opengis.wcs.dto.GetCapabilities;

public class GetCapabilitiesFixtureFactory {

    public static GetCapabilities.Capabilities createValidGetCapabilities() {

        GetCapabilities.Capabilities capabilities = new GetCapabilities.Capabilities();

        capabilities.version = "2.0.1";

        // --------------------------------------------------------------------
        // Service identification
        // --------------------------------------------------------------------
        GetCapabilities.ServiceIdentification serviceIdentification =
                new GetCapabilities.ServiceIdentification();

        serviceIdentification.title =
                "Download service (WCS) of fields from the limited area French numerical weather prediction model.";

        serviceIdentification.owsAbstract =
                "The service allows to download in grid point analysis and forecast.";

        serviceIdentification.serviceType = "WCS";

        serviceIdentification.serviceTypeVersion = "2.0.1";

        serviceIdentification.profiles =
                List.of(
                        "http://www.opengis.net/spec/WCS/2.0/conf/core",
                        "http://www.opengis.net/spec/GMLCOV_geotiff-coverages/1.0/conf/geotiff-coverage");

        capabilities.serviceIdentification = serviceIdentification;

        // --------------------------------------------------------------------
        // Service provider
        // --------------------------------------------------------------------
        GetCapabilities.Address address = new GetCapabilities.Address();

        address.electronicMailAddress = "support.inspire@meteo.fr";

        GetCapabilities.ContactInfo contactInfo = new GetCapabilities.ContactInfo();

        contactInfo.address = address;

        GetCapabilities.ServiceContact serviceContact = new GetCapabilities.ServiceContact();

        serviceContact.contactInfo = contactInfo;

        GetCapabilities.ServiceProvider serviceProvider = new GetCapabilities.ServiceProvider();

        serviceProvider.providerName = "NMC FRANCE – Météo-France";

        serviceProvider.serviceContact = serviceContact;

        capabilities.serviceProvider = serviceProvider;

        // --------------------------------------------------------------------
        // Operations metadata
        // --------------------------------------------------------------------
        capabilities.operationsMetadata = new GetCapabilities.OperationsMetadata();

        capabilities.operationsMetadata.operations =
                List.of(
                        createOperation(
                                "GetCapabilities",
                                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-INDIEN-WCS/GetCapabilities"),
                        createOperation(
                                "DescribeCoverage",
                                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-INDIEN-WCS/DescribeCoverage"),
                        createOperation(
                                "GetCoverage",
                                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-INDIEN-WCS/GetCoverage"));

        // --------------------------------------------------------------------
        // Service metadata
        // --------------------------------------------------------------------
        GetCapabilities.MetadataUrl metadataUrl = new GetCapabilities.MetadataUrl();

        metadataUrl.url =
                "https://public-api.meteofrance.fr/public/arome/wcs/MF-NWP-HIGHRES-AROME-OM-0025-INDIEN-WCS/GetMetadata";

        metadataUrl.mediaType = "application/vnd.iso.19139+xml";

        GetCapabilities.LanguageContainer defaultLanguage = new GetCapabilities.LanguageContainer();

        defaultLanguage.language = "eng";

        GetCapabilities.LanguageContainer frenchLanguage = new GetCapabilities.LanguageContainer();

        frenchLanguage.language = "fre";

        GetCapabilities.LanguageContainer englishLanguage = new GetCapabilities.LanguageContainer();

        englishLanguage.language = "eng";

        GetCapabilities.SupportedLanguages supportedLanguages =
                new GetCapabilities.SupportedLanguages();

        supportedLanguages.defaultLanguage = defaultLanguage;

        supportedLanguages.supportedLanguages = List.of(frenchLanguage, englishLanguage);

        GetCapabilities.LanguageContainer responseLanguage =
                new GetCapabilities.LanguageContainer();

        responseLanguage.language = "eng";

        GetCapabilities.ExtendedCapabilities extendedCapabilities =
                new GetCapabilities.ExtendedCapabilities();

        extendedCapabilities.metadataUrl = metadataUrl;
        extendedCapabilities.supportedLanguages = supportedLanguages;
        extendedCapabilities.responseLanguage = responseLanguage;

        GetCapabilities.Extension extension = new GetCapabilities.Extension();

        extension.extendedCapabilities = extendedCapabilities;

        GetCapabilities.ServiceMetadata serviceMetadata = new GetCapabilities.ServiceMetadata();

        serviceMetadata.formatsSupported = List.of("image/tiff", "application/wmo-grib");

        serviceMetadata.extension = extension;

        capabilities.serviceMetadata = serviceMetadata;

        // --------------------------------------------------------------------
        // Coverage summaries
        // --------------------------------------------------------------------
        GetCapabilities.CoverageSummary summary1 = new GetCapabilities.CoverageSummary();

        summary1.title = "Altitude of dew point temperature at 273.15K";

        summary1.coverageId = "ALTITUDE_TPW__ISOTHERMAL_LEVEL_27315___2026-05-21T00.00.00Z";

        summary1.coverageSubtype = "ReferenceableGridCoverage";

        GetCapabilities.CoverageSummary summary2 = new GetCapabilities.CoverageSummary();

        summary2.title = "Altitude of dew point temperature at 273.15K";

        summary2.coverageId = "ALTITUDE_TPW__ISOTHERMAL_LEVEL_27315___2026-05-21T06.00.00Z";

        summary2.coverageSubtype = "ReferenceableGridCoverage";

        GetCapabilities.Contents contents = new GetCapabilities.Contents();

        contents.coverageSummaries = List.of(summary1, summary2);

        capabilities.contents = contents;

        return capabilities;
    }

    private static GetCapabilities.Operation createOperation(String name, String href) {

        GetCapabilities.GetMethod getMethod = new GetCapabilities.GetMethod();

        getMethod.href = href;

        GetCapabilities.Http http = new GetCapabilities.Http();

        http.get = getMethod;

        GetCapabilities.Dcp dcp = new GetCapabilities.Dcp();

        dcp.http = http;

        GetCapabilities.Operation operation = new GetCapabilities.Operation();

        operation.name = name;
        operation.dcp = dcp;

        return operation;
    }
}
