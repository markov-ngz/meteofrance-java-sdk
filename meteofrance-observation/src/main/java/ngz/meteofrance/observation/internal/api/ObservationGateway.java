package ngz.meteofrance.observation.internal.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import ngz.meteofrance.core.auth.Token;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.core.exception.URIBuildingException;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.mapper.ProductDataMapper;
import ngz.meteofrance.observation.model.ProductData;
import org.apache.hc.core5.net.URIBuilder;

public class ObservationGateway implements ObservationApi {

    private final MeteoFranceClient client;
    private static final String BASE_ENDPOINT = "/public/DPObs/v2/";

    public ObservationGateway(MeteoFranceClient meteoFranceClient) {
        this.client = meteoFranceClient;
        new Token("b", Instant.now());
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
        // 1. Explicit Precondition check: Fail immediately if params is completely missing
        Objects.requireNonNull(params, "Parameters array cannot be null");

        String uri = BASE_ENDPOINT + path;
        try {
            URIBuilder builder = new URIBuilder(uri);
            for (String[] param : params) {

                // 2. Fail if the 2D array structure is broken
                if (param == null || param.length < 2) {
                    throw new URIBuildingException(
                            "Each parameter must be a non-null pair of [key, value]. Found: "
                                    + Arrays.deepToString(param));
                }

                // 3. Fail if the key is missing (you can't have a query param without a key!)
                if (param[0] == null) {
                    throw new URIBuildingException("Query parameter key cannot be null");
                }

                // Only allow param[1] (the value) to be checked for business logic
                if (param[1] != null) {
                    builder.addParameter(param[0], param[1]);
                }
            }
            return builder.build();
        } catch (URISyntaxException e) {
            throw new URIBuildingException(
                    String.format(
                            "Failed to build URI for path [%s] with params %s",
                            path, Arrays.deepToString(params)),
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
                        "station/infrahoraire-6m",
                        new String[][] {
                            {"id_station", stationId},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getSubHourObservationsByStation(
            String stationId, Instant time, Format format) {
        return get(
                buildUri(
                        "station/infrahoraire-6m",
                        new String[][] {
                            {"id_station", stationId},
                            {"date", time.toString()},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getHourlyObservationByStation(String stationId, Format format) {
        return get(
                buildUri(
                        "station/horaire",
                        new String[][] {
                            {"id_station", stationId},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getHourlyObservationByStation(
            String stationId, Instant date, Format format) {
        return get(
                buildUri(
                        "station/horaire",
                        new String[][] {
                            {"id_station", stationId},
                            {"date", date.toString()},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData listSynopStations(Format format) {
        return get(buildUri("liste-stations-synop", new String[][] {{"format", format.name()}}));
    }

    @Override
    public ProductData getSynopObservations(Format format) {
        return get(buildUri("synop", new String[][] {{"format", format.name()}}));
    }

    @Override
    public ProductData getSynopObservations(String stationId, Format format) {
        return get(
                buildUri(
                        "synop",
                        new String[][] {
                            {"id_station", stationId},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getSynopObservations(
            String stationId, Instant startTime, Instant endTime, Format format) {
        return get(
                buildUri(
                        "synop",
                        new String[][] {
                            {"id_station", stationId},
                            {"date_debut", startTime.toString()},
                            {"date_fin", endTime.toString()},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData listBuoys(Format format) {
        return get(buildUri("liste-bouees", new String[][] {{"format", format.name()}}));
    }

    @Override
    public ProductData getBuoyObservation(Format format) {
        return get(buildUri("bouee", new String[][] {{"format", format.name()}}));
    }

    @Override
    public ProductData getBuoyObservation(List<String> buoyIds, Format format) {
        return get(
                buildUri(
                        "bouee",
                        new String[][] {
                            {"id_bouees", String.join(",", buoyIds)},
                            {"format", format.name()}
                        }));
    }

    @Override
    public ProductData getBuoyObservation(
            List<String> buoyIds, Instant startTime, Instant endTime, Format format) {
        return get(
                buildUri(
                        "bouee",
                        new String[][] {
                            {"id_bouees", String.join(",", buoyIds)},
                            {"date_debut", startTime.toString()},
                            {"date_fin", endTime.toString()},
                            {"format", format.name()}
                        }));
    }
}
