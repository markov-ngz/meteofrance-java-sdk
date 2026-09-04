package ngz.meteofrance.paquet.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.core.client.MeteoFranceClientConfig;
import ngz.meteofrance.core.client.MeteoFranceClientFactory;
import ngz.meteofrance.paquet.model.ProductData;
import ngz.meteofrance.paquet.model.ProductInfo;
import ngz.meteofrance.paquet.service.ProductInfoService;
import ngz.meteofrance.paquet.service.ProductInfoServiceFactory;
import org.junit.jupiter.api.Test;

public class ProductInfoServiceE2ETest {

    /** Generate Yesterday date at 6pm . */
    Instant generateReferenceTime() {
        return LocalDate.now().minusDays(1).atTime(18, 0).atZone(ZoneId.of("UTC")).toInstant();
    }

    @Test
    void shouldGetAvailableForecastsForAromePaquet() throws Exception {

        String previnum = "DPPaquetAROME-OM";
        String model = "AROME-OM-NCALED";
        String grid = "0.025";
        String packageName = "SP1";
        Instant referenceTime = generateReferenceTime();

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        ProductInfoService service = ProductInfoServiceFactory.create(meteoFranceClient);

        List<ProductInfo> productInfos =
                service.getAvailableForecasts(previnum, model, grid, packageName, referenceTime);

        assertThat(productInfos)
                .isNotEmpty()
                .allSatisfy(
                        forecast -> {
                            assertThat(forecast.getLocation()).isNotBlank();
                            assertThat(forecast.getReferenceTime()).isEqualTo(referenceTime);
                        });
    }

    @Test
    void shouldDownloadByHrefForAromePaquet() throws Exception {

        String href =
                "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/SP1/productOMNC?&referencetime="
                        + generateReferenceTime().toString()
                        + "&time=001H&format=grib2";

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        ProductInfoService service = ProductInfoServiceFactory.create(meteoFranceClient);

        ProductData productData = service.downloadForecastDataByHref(href);

        assertThat(productData.getFileName()).isNotEmpty();
        assertThat(productData.getLocation()).isEqualTo(href);
        assertThat(productData.getContent()).hasSizeGreaterThan(2000);

        Files.write(Path.of(productData.getFileName()), productData.getContent());
    }

    @Test
    void shouldGetAvailableForecastsForWavePaquet() throws Exception {

        String previnum = "DPPaquetWAVESMODELS";
        String model = "HYCOM2D-MARP";
        String grid = "0.04";
        String packageName = "SP1";
        Instant referenceTime = generateReferenceTime();

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        ProductInfoService service = ProductInfoServiceFactory.create(meteoFranceClient);

        List<ProductInfo> productInfos =
                service.getAvailableForecasts(previnum, model, grid, packageName, referenceTime);

        assertThat(productInfos)
                .isNotEmpty()
                .allSatisfy(
                        productInfo -> {
                            assertThat(productInfo.getLocation()).isNotBlank();

                            assertThat(productInfo.getReferenceTime()).isEqualTo(referenceTime);
                        });
    }

    @Test
    void shouldDownloadByHrefForWavePaquet() throws Exception {

        String href =
                "https://public-api.meteofrance.fr/previnum/DPPaquetWAVESMODELS/models/HYCOM2D-MARP/grids/0.04/packages/SP1/productHMARP?&referencetime="
                        + generateReferenceTime().toString()
                        + "&time=001H&format=grib2";

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        ProductInfoService service = ProductInfoServiceFactory.create(meteoFranceClient);

        ProductData productData = service.downloadForecastDataByHref(href);

        assertThat(productData.getFileName()).isNotEmpty();
        assertThat(productData.getLocation()).isEqualTo(href);
        assertThat(productData.getContent()).hasSizeGreaterThan(2000);

        Files.write(Path.of(productData.getFileName()), productData.getContent());
    }

    // @Test
    // void shouldThrowWhenInexistentReferenceTime() throws Exception {
    //     String model = "AROME-OM-NCALED";
    //     String grid = "0.025";
    //     String packageName = "SP1";
    //     Instant referenceTime = Instant.parse("2042-05-08T18:00:00Z");

    //     EnvironmentConfig envConfig = new EnvironmentConfig();
    //     ApiClientConfig config = new ApiClientConfig(envConfig);

    //     ProductInfoService service = ProductInfoServiceFactory.create(config.asMap());

    //     assertThatThrownBy(
    //                     () ->
    //                             service.getAvailableForecasts(
    //                                     model, grid, packageName, referenceTime))
    //             .isInstanceOf(AromeApiException.class);
    // }
}
