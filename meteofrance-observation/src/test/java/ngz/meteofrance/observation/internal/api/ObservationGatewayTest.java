package ngz.meteofrance.observation.internal.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.api.ObservationPackageApi;
import ngz.meteofrance.observation.model.ProductData;
import org.junit.jupiter.api.Test;

public class ObservationGatewayTest {

    @Test
    void shouldGetHourlyObservationByStation() throws Exception {

        MeteoFranceClient client = mock(MeteoFranceClient.class);

        HttpResponse<byte[]> httpResponse =
                mock(HttpResponse.class); // HttpResponse as generic type like HttpResponse<String>

        byte[] csvContent =
                Files.readAllBytes(Path.of("src/test/resources/observation/validObservation.csv"));

        Map<String, List<String>> headerMap =
                Map.of(
                        "content-disposition",
                                List.of("attachment; filename=paquetobs-horaire_5.csv"),
                        "content-type", List.of("text/plain; charset=utf-8"),
                        "date", List.of("Fri,12 Jun 2026 16:44:33 GMT"));
        HttpHeaders realHeaders = HttpHeaders.of(headerMap, (key, value) -> true);

        String uri = "https://domain/location";

        when(httpResponse.body()).thenReturn(csvContent);
        when(httpResponse.uri()).thenReturn(URI.create(uri));
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.headers()).thenReturn(realHeaders);
        when(client.request(anyString(), eq(HttpResponse.BodyHandlers.ofByteArray())))
                .thenReturn(httpResponse);

        ObservationPackageApi observationApi = new ObservationPackageGateway(client);

        String departmentId = "988";

        ProductData productData =
                observationApi.getHourlyObservationByDepartment(departmentId, Format.csv);

        assertThat(productData.getLocation()).isEqualTo(uri);

        assertThat(productData.getFileName()).isEqualTo("paquetobs-horaire_5.csv");

        assertThat(productData.getFormat()).isEqualTo(Format.csv.name().toLowerCase());

        assertThat(productData.getContent().length).isGreaterThan(0);
    }

    @Test
    void shouldMapFileNameBetweenBrackets() throws Exception {

        MeteoFranceClient client = mock(MeteoFranceClient.class);

        HttpResponse<byte[]> httpResponse =
                mock(HttpResponse.class); // HttpResponse as generic type like HttpResponse<String>

        byte[] csvContent =
                Files.readAllBytes(Path.of("src/test/resources/observation/validObservation.csv"));

        Map<String, List<String>> headerMap =
                Map.of(
                        "content-disposition",
                                List.of("attachment; filename=\"paquetobs-horaire_5.csv\""),
                        "content-type", List.of("text/plain; charset=utf-8"),
                        "date", List.of("Fri,12 Jun 2026 16:44:33 GMT"));
        HttpHeaders realHeaders = HttpHeaders.of(headerMap, (key, value) -> true);

        String uri = "https://domain/location";

        when(httpResponse.body()).thenReturn(csvContent);
        when(httpResponse.uri()).thenReturn(URI.create(uri));
        when(httpResponse.statusCode()).thenReturn(200);
        when(httpResponse.headers()).thenReturn(realHeaders);
        when(client.request(anyString(), eq(HttpResponse.BodyHandlers.ofByteArray())))
                .thenReturn(httpResponse);

        ObservationPackageApi observationApi = new ObservationPackageGateway(client);

        String departmentId = "988";

        ProductData productData =
                observationApi.getHourlyObservationByDepartment(departmentId, Format.csv);

        assertThat(productData.getLocation()).isEqualTo(uri);

        assertThat(productData.getFileName()).isEqualTo("paquetobs-horaire_5.csv");

        assertThat(productData.getFormat()).isEqualTo(Format.csv.name().toLowerCase());

        assertThat(productData.getContent().length).isGreaterThan(0);
    }
    // test : mock the client and status and headers
    // check parsing and product mapping
}
