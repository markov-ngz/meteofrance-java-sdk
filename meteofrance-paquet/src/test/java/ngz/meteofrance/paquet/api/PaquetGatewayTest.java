package ngz.meteofrance.paquet.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.internal.api.PaquetGateway;
import org.junit.jupiter.api.Test;

class PaquetGatewayTest {

    @Test
    void shouldDescribeShortenPaquet() throws Exception {

        MeteoFranceClient client = mock(MeteoFranceClient.class);

        HttpResponse<String> httpResponse =
                mock(HttpResponse.class); // HttpResponse as generic type like HttpResponse<String>

        String jsonResponse =
                Files.readString(
                        Path.of("src/test/resources/paquet/arome/validApiDescribePaquet.json"));

        when(httpResponse.body()).thenReturn(jsonResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        when(client.request(anyString(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        PaquetApi api = new PaquetGateway(client);

        MeteoFranceGridResponse response = api.describePaquet("DEF", "ABC", "0.001", "SP1");

        // assert that it is not null
        assertThat(response.getTitle())
                .isEqualTo(
                        "Paramêtres courants en niveaux hauteur disponibles pour le modèle AROME-OM-NCALED 0.025");

        assertThat(response.getLinks().size()).isEqualTo(13);
    }

    @Test
    void shouldDescribePaquet() throws Exception {

        MeteoFranceClient client = mock(MeteoFranceClient.class);

        HttpResponse<String> httpResponse =
                mock(HttpResponse.class); // HttpResponse as generic type like HttpResponse<String>

        String jsonResponse =
                Files.readString(
                        Path.of("src/test/resources/paquet/arome/validApiDescribePaquet2.json"));

        when(httpResponse.body()).thenReturn(jsonResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        when(client.request(anyString(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        PaquetApi api = new PaquetGateway(client);

        MeteoFranceGridResponse response = api.describePaquet("DEF", "ABC", "0.001", "SP1");

        // assert that it is not null
        assertThat(response.getTitle())
                .isEqualTo(
                        "Paramêtres courants en niveaux hauteur disponibles pour le modèle AROME-OM-NCALED 0.025");

        assertThat(response.getLinks().size()).isEqualTo(13);
    }

    //
    //    @Test
    //    void shouldRaiseAnErrorwhenMappingfails() throws Exception {
    //
    //        MeteoFranceClient client = mock(MeteoFranceClient.class);
    //
    //        HttpResponse<String> httpResponse =
    //                mock(HttpResponse.class); // HttpResponse as generic type like
    // HttpResponse<String>
    //
    //        String jsonResponse =
    //                Files.readString(
    //
    // Path.of("src/test/resources/paquet/arome/invalidDescribePaquet.json"));
    //
    //        when(httpResponse.body()).thenReturn(jsonResponse);
    //
    //        when(client.request(
    //                        eq("GET"), anyString(), isNull(),
    // eq(HttpResponse.BodyHandlers.ofString())))
    //                .thenReturn(httpResponse);
    //
    //        AromePaquetApi api = new AromePaquetGateway(client);
    //
    //        assertThatThrownBy(() -> api.describePaquet("ABC", "0.001", "SP1"))
    //                .isInstanceOf(AromeApiException.class);
    //    }

    @Test
    void shouldListLeadTimePaquet() throws Exception {
        MeteoFranceClient client = mock(MeteoFranceClient.class);

        HttpResponse<String> httpResponse =
                mock(HttpResponse.class); // HttpResponse as generic type like HttpResponse<String>

        String jsonResponse =
                Files.readString(
                        Path.of("src/test/resources/paquet/arome/validAvailableProductInfo.json"));

        when(httpResponse.body()).thenReturn(jsonResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        when(client.request(anyString(), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        PaquetApi api = new PaquetGateway(client);

        MeteoFranceGridResponse response =
                api.listProductInfo(
                        "DEF", "ABC", "0.001", "SP1", Instant.parse("2025-01-01T00:00:00Z"));

        // assert that it is not null
        assertThat(response.getTitle())
                .isEqualTo(
                        "Réseau du 2026-06-10 à 00:00:00 UTC pour le modèle AROME-OM-NCALED 0.025");

        assertThat(response.getLinks().size()).isEqualTo(5);
    }
}
