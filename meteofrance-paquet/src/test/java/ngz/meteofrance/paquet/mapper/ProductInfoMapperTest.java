package ngz.meteofrance.paquet.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.model.ProductInfo;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

public class ProductInfoMapperTest {

    @Test
    void shouldMapValidvalue() {

        MeteoFranceGridResponse response =
                MeteoFranceGridResponseFixture.validAvailableForecast().build();

        String previnum = "DEF";
        String model = " ABC";
        String grid = "0.001";
        String packageName = "SP1";

        List<ProductInfo> productInfos =
                ProductInfoMapper.mapFromMeteoFranceGridResponse(
                        response, previnum, model, grid, packageName);

        assertThat(productInfos.size()).as("Size should be ").isEqualTo(4);
        assertThat(productInfos.stream().map(ProductInfo::getTime).collect(Collectors.toList()))
                .as("Times should contain the following ")
                .containsExactlyInAnyOrder(0, 1, 2, 3);
        assertThat(
                        productInfos.stream()
                                .map(ProductInfo::getInsertTime)
                                .collect(Collectors.toList()))
                .as("Times should contain the following ")
                .containsExactlyInAnyOrder(
                        Instant.parse("2026-06-10T06:23:55Z"),
                        Instant.parse("2026-06-10T06:25:04Z"),
                        Instant.parse("2026-06-10T06:26:01Z"),
                        Instant.parse("2026-06-10T06:26:40Z"));
    }

    @Test
    void shouldMapProductInfo() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String rawResponse =
                Files.readString(
                        Path.of("src/test/resources/paquet/arome/validAvailableProductInfo.json"));
        MeteoFranceGridResponse response =
                objectMapper.readValue(rawResponse, MeteoFranceGridResponse.class);

        String previnum = "DEF";
        String model = " ABC";
        String grid = "0.001";
        String packageName = "SP1";

        List<ProductInfo> productInfos =
                ProductInfoMapper.mapFromMeteoFranceGridResponse(
                        response, previnum, model, grid, packageName);

        assertThat(productInfos.size()).as("Size should be ").isEqualTo(4);
    }

    @Test
    void shouldMapProductInfo2() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String rawResponse =
                Files.readString(
                        Path.of("src/test/resources/paquet/arome/validAvailableProductInfo2.json"));
        MeteoFranceGridResponse response =
                objectMapper.readValue(rawResponse, MeteoFranceGridResponse.class);

        String previnum = "DEF";
        String model = " ABC";
        String grid = "0.001";
        String packageName = "SP1";

        List<ProductInfo> productInfos =
                ProductInfoMapper.mapFromMeteoFranceGridResponse(
                        response, previnum, model, grid, packageName);

        assertThat(productInfos.size()).as("Size should be ").isEqualTo(52);
    }
}
