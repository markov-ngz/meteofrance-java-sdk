package ngz.meteofrance.observation.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.core.client.MeteoFranceClientConfig;
import ngz.meteofrance.core.client.MeteoFranceClientFactory;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.Station;
import ngz.meteofrance.observation.service.StationService;
import ngz.meteofrance.observation.service.StationServiceFactory;
import org.junit.jupiter.api.Test;

public class StationServiceE2ETest {

    @Test
    void shouldListStations() throws Exception {

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        StationService stationService = StationServiceFactory.create(meteoFranceClient);

        List<Station> stations = stationService.listStations();

        assertThat(stations.size()).isGreaterThan(0);
    }

    @Test
    void shouldDownloadCsv() throws Exception {

        EnvironmentConfig envConfig = new EnvironmentConfig();
        ApiClientConfig config = new ApiClientConfig(envConfig);

        MeteoFranceClientConfig mfConfig =
                MeteoFranceClientConfig.builder(config.getApplicationId()).build();

        MeteoFranceClient meteoFranceClient = MeteoFranceClientFactory.create(mfConfig);

        StationService stationService = StationServiceFactory.create(meteoFranceClient);

        ProductData productData =
                stationService.downloadObservationsByDepartmentId("988", Format.csv);
    }
}
