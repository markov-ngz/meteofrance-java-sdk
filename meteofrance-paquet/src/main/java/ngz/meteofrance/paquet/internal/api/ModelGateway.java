package ngz.meteofrance.paquet.internal.api;

import java.net.http.HttpResponse;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.api.ModelApi;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import tools.jackson.databind.ObjectMapper;

/** Implementation of the ModelApi. */
public class ModelGateway implements ModelApi {

    private final MeteoFranceClient meteoFranceClient;
    private final ObjectMapper objectMapper;

    public ModelGateway(MeteoFranceClient meteoFranceClient, String model) {
        this.meteoFranceClient = meteoFranceClient;

        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MeteoFranceGridResponse describeModel(String previnum, String model) {

        String url = "/previnum/" + previnum + "/models/" + model;

        HttpResponse<String> response =
                meteoFranceClient.request(url, HttpResponse.BodyHandlers.ofString());

        return this.objectMapper.readValue(response.body(), MeteoFranceGridResponse.class);
    }
}
