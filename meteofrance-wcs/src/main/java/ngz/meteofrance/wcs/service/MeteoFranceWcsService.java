package ngz.meteofrance.wcs.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.wcs.utils.QueryParameterUtil;
import ngz.opengis.wcs.dto.DescribeCoverage;
import ngz.opengis.wcs.dto.GetCapabilities;
import ngz.opengis.wcs.exception.WcsException;
import ngz.opengis.wcs.exception.WcsMappingException;
import ngz.opengis.wcs.exception.WcsParsingException;
import ngz.opengis.wcs.exception.WcsServiceException;
import ngz.opengis.wcs.exception.WcsValidationException;
import ngz.opengis.wcs.mapper.CapabilitiesMapper;
import ngz.opengis.wcs.mapper.CoverageMapper;
import ngz.opengis.wcs.model.Capabilities;
import ngz.opengis.wcs.model.Coverage;
import ngz.opengis.wcs.parser.CoverageDescriptionXmlParser;
import ngz.opengis.wcs.parser.GetCapabilitiesXmlParser;
import ngz.opengis.wcs.service.WcsService;

public class MeteoFranceWcsService implements WcsService {

    private final MeteoFranceClient client;
    private final GetCapabilitiesXmlParser capabilitiesXmlParser; // use default parser
    private final CapabilitiesMapper capabilitiesMapper;
    private final CoverageDescriptionXmlParser coverageDescriptionXmlParser; //
    private final CoverageMapper coverageMapper;
    private final String model;
    private final ModelType modelType;

    // Clean format template (no leading/trailing slashes)
    private static final String BASE_ENDPOINT_FORMAT = "public/%s/wcs/%s/";
    private final String baseEndpoint;

    // Operation locations act as relative paths (no leading slashes needed)
    private static final String GET_CAPABILITIES_LOCATION = "GetCapabilities";
    private static final String DESCRIBE_COVERAGE_LOCATION = "DescribeCoverage";
    private static final String GET_COVERAGE_LOCATION = "GetCoverage";
    private static final Map<String, String> BASE_QUERY_PARAMS =
            Map.of("service", "WCS", "version", "2.0.1");

    public MeteoFranceWcsService(MeteoFranceClient client, ModelType modelType, String model) {

        this.client = client;
        this.capabilitiesXmlParser =
                new GetCapabilitiesXmlParser(); // why no dependency injection ? How to know if it
        // is relevant
        this.coverageDescriptionXmlParser = new CoverageDescriptionXmlParser();
        this.coverageMapper = new CoverageMapper();
        this.capabilitiesMapper = new CapabilitiesMapper();

        this.model = model;
        this.modelType = modelType;

        this.baseEndpoint = resolveBaseEndpoint(modelType, model);
    }

    public String getModel() {
        return this.model;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    private String resolveBaseEndpoint(ModelType modelType, String modelName) {
        // Uses the lower-case path value mapped within the enum
        return String.format(BASE_ENDPOINT_FORMAT, modelType.getPathValue(), modelName);
    }

    private static Map<String, String> withLanguage(Map<String, String> base) {
        Map<String, String> extended = new HashMap<>(base);
        extended.put("language", "eng");
        return Collections.unmodifiableMap(extended);
    }

    @Override
    public Capabilities getCapabilities() throws WcsException {
        // Use URI.create().resolve() to safely stitch the paths together without slash conflicts
        String endpoint =
                URI.create(this.baseEndpoint).resolve(GET_CAPABILITIES_LOCATION).toString();

        String queryParameters =
                QueryParameterUtil.encodeQueryParams(withLanguage(BASE_QUERY_PARAMS));

        String endpointWithQueryParameters = endpoint + "?" + queryParameters;

        System.err.println(endpointWithQueryParameters);

        // Execute the request via the client
        HttpResponse<byte[]> response =
                this.client.request(
                        endpointWithQueryParameters, HttpResponse.BodyHandlers.ofByteArray());

        // tmp local file writing to check xml errors as API is inconsistent
        Path outputPath = Paths.get("debug4.xml");

        // Write the byte array to the file
        try {
            Files.write(outputPath, response.body());

            GetCapabilities.Capabilities parsedCapabilities =
                    this.capabilitiesXmlParser.parse(response.body());

            return this.capabilitiesMapper.mapFromDto(parsedCapabilities);

        } catch (WcsValidationException | WcsParsingException | WcsMappingException e) {
            throw e; // Already the right type, rethrow as-is
        } catch (IOException e) {
            throw new WcsServiceException(
                    "I/O error while writing debug file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new WcsServiceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    @Override
    public Coverage describeCoverage(String coverageId) throws WcsException {

        // Use URI.create().resolve() to safely stitch the paths together without slash conflicts
        String endpoint =
                URI.create(this.baseEndpoint).resolve(DESCRIBE_COVERAGE_LOCATION).toString();

        System.err.println(this.baseEndpoint);

        // Add coverageId query parameter
        Map<String, String> extended = new HashMap<>(BASE_QUERY_PARAMS);
        extended.put("coverageId", coverageId);

        String queryParameters = QueryParameterUtil.encodeQueryParams(extended);

        String endpointWithQueryParameters = endpoint + "?" + queryParameters;

        System.err.println(endpointWithQueryParameters);

        // Execute the request via the client
        HttpResponse<byte[]> response =
                this.client.request(
                        endpointWithQueryParameters, HttpResponse.BodyHandlers.ofByteArray());

        // tmp local file writing to check xml errors as API is inconsistent
        Path outputPath = Paths.get("debugParsing.xml");

        try {
            Files.write(outputPath, response.body());

            DescribeCoverage.CoverageDescriptions coverageDescriptions =
                    this.coverageDescriptionXmlParser.parse(response.body());

            return coverageMapper.mapFromDto(coverageDescriptions);

        } catch (WcsValidationException | WcsParsingException | WcsMappingException e) {
            throw e; // Already the right type, rethrow as-is
        } catch (IOException e) {
            throw new WcsServiceException(
                    "I/O error while writing debug file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new WcsServiceException("Unexpected error: " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] getCoverage(String coverageUrl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Java Standard idiomatic Enum: UPPERCASE constants with internal string values
    public enum ModelType {
        AROME("arome"),
        ARPEGE("arpege"),
        WAVESMODELS("wavesmodels");

        private final String pathValue;

        ModelType(String pathValue) {
            this.pathValue = pathValue;
        }

        public String getPathValue() {
            return this.pathValue;
        }
    }
}
