package ngz.meteofrance.paquet.internal.api;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.api.PaquetApi;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import tools.jackson.databind.ObjectMapper;

/** Implementation of the PaquetApi. */
public class PaquetGateway implements PaquetApi {

    private final MeteoFranceClient meteoFranceClient;
    private final ObjectMapper objectMapper;

    public PaquetGateway(MeteoFranceClient meteoFranceClient) {
        this.meteoFranceClient = meteoFranceClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public MeteoFranceGridResponse listPaquets(String previnum, String model, String grid) {
        // 1. URL encode the grid parameter to handle special characters safely
        String encodedGrid =
                java.net.URLEncoder.encode(grid, java.nio.charset.StandardCharsets.UTF_8);

        // 2. Format the URL by injecting the encoded grid string
        String url =
                String.format(
                        "/previnum/%s/v1/models/%s/grids/%s/packages",
                        previnum, model, encodedGrid);

        HttpResponse<String> response =
                meteoFranceClient.request(url, HttpResponse.BodyHandlers.ofString());

        return this.objectMapper.readValue(response.body(), MeteoFranceGridResponse.class);
    }

    @Override
    public MeteoFranceGridResponse describePaquet(
            String previnum, String model, String grid, String packageName) {

        // 1. URL encode the grid parameter to handle special characters safely
        String encodedGrid =
                java.net.URLEncoder.encode(grid, java.nio.charset.StandardCharsets.UTF_8)
                        .replaceAll("\\+", "%20");
        String encodedPaquet =
                java.net.URLEncoder.encode(packageName, java.nio.charset.StandardCharsets.UTF_8)
                        .replaceAll("\\+", "%20");

        // 2. Format the URL by injecting the encoded grid string
        String url =
                String.format(
                        "/previnum/%s/v1/models/%s/grids/%s/packages/%s",
                        previnum, model, encodedGrid, encodedPaquet);

        HttpResponse<String> response =
                meteoFranceClient.request(url, HttpResponse.BodyHandlers.ofString());

        MeteoFranceGridResponse meteoFranceGridResponse =
                this.objectMapper.readValue(response.body(), MeteoFranceGridResponse.class);

        return meteoFranceGridResponse;
    }

    @Override
    public MeteoFranceGridResponse listProductInfo(
            String previnum, String model, String grid, String packageName, Instant referenceTime) {

        // 1. URL encode the grid parameter to handle special characters safely
        String encodedGrid =
                java.net.URLEncoder.encode(grid, java.nio.charset.StandardCharsets.UTF_8);
        String encodedPaquet =
                java.net.URLEncoder.encode(packageName, java.nio.charset.StandardCharsets.UTF_8);

        // 2. Format the URL by injecting the encoded grid string
        String url =
                String.format(
                        "/previnum/%s/v1/models/%s/grids/%s/packages/%s?referencetime=%s",
                        previnum,
                        model,
                        encodedGrid,
                        encodedPaquet,
                        instantToApiString(referenceTime));

        HttpResponse<String> response =
                meteoFranceClient.request(url, HttpResponse.BodyHandlers.ofString());

        return this.objectMapper.readValue(response.body(), MeteoFranceGridResponse.class);
    }

    private String instantToApiString(Instant value) {
        String apiString = DateTimeFormatter.ISO_INSTANT.format(value);
        return java.net.URLEncoder.encode(apiString, java.nio.charset.StandardCharsets.UTF_8);
    }
}
