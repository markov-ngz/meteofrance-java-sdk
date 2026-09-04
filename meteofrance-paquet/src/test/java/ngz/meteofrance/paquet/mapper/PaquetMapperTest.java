package ngz.meteofrance.paquet.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.model.Paquet;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class PaquetMapperTest {

    @Test
    void shouldMapValidResponse() throws Exception {

        MeteoFranceGridResponse response =
                MeteoFranceGridResponseFixture.validPaquetReferenceTimes().build();

        String previnum = "DEF";
        String model = " ABC";
        String grid = "0.001";
        String packageName = "SP1";

        Paquet paquet =
                PaquetMapper.mapFromMeteoFranceGridResponse(
                        response, previnum, model, grid, packageName);

        assertThat(paquet.getReferenceTimes())
                .as("Reference Time of an AromePaquet be a list of Instant")
                .containsExactlyInAnyOrder(
                        Instant.parse("2026-06-07T06:00:00Z"),
                        Instant.parse("2026-06-07T12:00:00Z"),
                        Instant.parse("2026-06-07T18:00:00Z"));
        assertThat(paquet.getId()).as("Id should be the packageName").isEqualTo(packageName);

        assertThat(paquet.getLink())
                .as("Link should be the one from rel 'self'")
                .isEqualTo(
                        "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1");
    }

    @Test
    void shouldMapDescribePaquet() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String rawResponse =
                Files.readString(
                        Path.of("src/test/resources/paquet/arome/validApiDescribePaquet2.json"));
        MeteoFranceGridResponse response =
                objectMapper.readValue(rawResponse, MeteoFranceGridResponse.class);

        String previnum = "DEF";
        String model = " ABC";
        String grid = "0.001";
        String packageName = "SP1";

        Paquet paquet =
                PaquetMapper.mapFromMeteoFranceGridResponse(
                        response, previnum, model, grid, packageName);
    }
}
