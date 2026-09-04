package ngz.meteofrance.paquet.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.core.client.MeteoFranceClientConfig;
import ngz.meteofrance.core.client.MeteoFranceClientFactory;
import ngz.meteofrance.paquet.model.Paquet;
import ngz.meteofrance.paquet.service.PaquetService;
import ngz.meteofrance.paquet.service.PaquetServiceFactory;
import org.junit.jupiter.api.Test;

public class PaquetServiceE2ETest {

    @Test
    void shouldDescribePackage() throws Exception {
        String previnum = "DPPaquetAROME-OM";
        String model = "AROME-OM-NCALED";
        String grid = "0.025";
        String packageName = "SP1";

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        PaquetService service = PaquetServiceFactory.create(meteoFranceClient);

        Paquet aromePackage = service.describePaquet(previnum, model, grid, packageName);

        assertThat(aromePackage.getId()).isEqualTo(packageName);
        assertThat(aromePackage.getGridId()).isEqualTo(grid);
        assertThat(aromePackage.getReferenceTimes().size()).isGreaterThan(0);
    }
    //
    //    @Test
    //    void shouldFailwhenApplicationIdiNotValid() throws Exception {
    //        String model = "AROME-OM-NCALED";
    //        String grid = "0.025";
    //        String packageName = "SP1";
    //
    //        Map<String, String> props = Map.of("applicationid", "ABCEFH");
    //
    //        AromePackageService service = AromePackageServiceFactory.create(props);
    //
    //        assertThatThrownBy(() -> service.describePackage(previnum, model, grid, packageName))
    //                .isInstanceOf(AromeApiException.class);
    //    }
    //
    //    @Test
    //    void shouldFailIfEndpointDoNotExist() {
    //        String fakeModel = "ABCEFGH";
    //        String grid = "0.025";
    //        String packageName = "SP1";
    //
    //        EnvironmentConfig envConfig = new EnvironmentConfig();
    //        ApiClientConfig config = new ApiClientConfig(envConfig);
    //
    //        AromePackageService service = AromePackageServiceFactory.create(config.asMap());
    //
    //        assertThatThrownBy(() -> service.describePackage(fakeModel, grid, packageName))
    //                .isInstanceOf(AromeApiException.class);
    //    }
}
