package ngz.meteofrance.paquet.internal.api;

import java.net.http.HttpResponse;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.api.GridApi;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import tools.jackson.databind.ObjectMapper;

/** Implementation of the GridApi. */
public class GridGateway implements GridApi {

    private final MeteoFranceClient meteoFranceClient;
    private final ObjectMapper objectMapper;

    public GridGateway(MeteoFranceClient meteoFranceClient) {
        this.meteoFranceClient = meteoFranceClient;
        this.objectMapper = new ObjectMapper();
    }

    private String buildGridEndpoint(String previnum, String model) {
        return "/previnum/" + previnum + "/models/" + model + "/grids/";
    }

    @Override
    public MeteoFranceGridResponse listGrids(String previnum, String model) {
        HttpResponse<String> response =
                meteoFranceClient.request(
                        buildGridEndpoint(previnum, model), HttpResponse.BodyHandlers.ofString());

        return this.objectMapper.readValue(response.body(), MeteoFranceGridResponse.class);
    }

    @Override
    public MeteoFranceGridResponse describeGrid(String previnum, String model, String grid) {

        String encodedGrid =
                java.net.URLEncoder.encode(grid, java.nio.charset.StandardCharsets.UTF_8);

        String specificGridEndpoint = buildGridEndpoint(previnum, model) + encodedGrid;

        HttpResponse<String> response =
                meteoFranceClient.request(
                        specificGridEndpoint, HttpResponse.BodyHandlers.ofString());

        MeteoFranceGridResponse meteoFranceGridResponse =
                this.objectMapper.readValue(response.body(), MeteoFranceGridResponse.class);

        return meteoFranceGridResponse;
    }
}
