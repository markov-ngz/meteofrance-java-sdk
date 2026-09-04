package ngz.meteofrance.observation.internal.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Arrays;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.core.exception.URIBuildingException;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.api.ObservationPackageApi;
import ngz.meteofrance.observation.mapper.ProductDataMapper;
import ngz.meteofrance.observation.model.ProductData;
import org.apache.hc.core5.net.URIBuilder;

public class ObservationPackageGateway implements ObservationPackageApi {

    private final MeteoFranceClient client;
    private static final String BASE_ENDPOINT = "/public/DPPaquetObs/v2/";

    public ObservationPackageGateway(MeteoFranceClient meteoFranceClient) {
        this.client = meteoFranceClient;
    }

    private ProductData get(String path) {
        HttpResponse<byte[]> response =
                this.client.request(BASE_ENDPOINT + path, HttpResponse.BodyHandlers.ofByteArray());
        return validated(response);
    }

    private ProductData get(URI uri) {
        HttpResponse<byte[]> response =
                this.client.request(uri.toString(), HttpResponse.BodyHandlers.ofByteArray());
        return validated(response);
    }

    private ProductData validated(HttpResponse<byte[]> response) {
        return ProductDataMapper.mapFromHttpResponse(response, response.uri().toString());
    }

    private URI buildUri(String path, String[][] params) {
        String uri = BASE_ENDPOINT + path;
        try {
            URIBuilder builder = new URIBuilder(uri);
            // Quick defensive check before looping to avoid a nasty NullPointerException
            if (params != null) {
                for (String[] param : params) {
                    // Defensive check: ensure the sub-array has the expected structure
                    if (param != null && param.length >= 2 && param[1] != null) {
                        builder.addParameter(param[0], param[1]);
                    }
                }
            }
            return builder.build();
        } catch (URISyntaxException e) {
            // Enriched exception message detailing exactly what input caused the failure
            throw new URIBuildingException(
                    String.format(
                            "Failed to build URI. Base/Path: [%s], Parameters: %s. Reason: %s",
                            uri, Arrays.deepToString(params), e.getReason()),
                    e);
        }
    }

    @Override
    public ProductData listStations() {
        return get("liste-stations");
    }

    @Override
    public ProductData getSubHourObservationsByStation(String stationId, Format format) {
        return get(
                buildUri(
                        "paquet/infrahoraire-6m",
                        new String[][] {
                            {"id-station", stationId},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getHourlyObservationByDepartment(String departmentId, Format format) {
        return get(
                buildUri(
                        "paquet/horaire",
                        new String[][] {
                            {"id-departement", departmentId},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getSubHourObservation(Instant time, Format format) {
        return get(
                buildUri(
                        "paquet/infrahoraire-6m",
                        new String[][] {
                            {"date", time.toString()},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getHourlyObservation(Instant date, Format format) {
        return get(
                buildUri(
                        "paquet/horaire",
                        new String[][] {
                            {"date", date.toString()},
                            {"format", format.name()}
                        }));
    }
}
