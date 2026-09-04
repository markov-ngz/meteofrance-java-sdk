package ngz.meteofrance.observation.service;

import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.api.ObservationPackageApi;
import ngz.meteofrance.observation.internal.api.ObservationGateway;
import ngz.meteofrance.observation.internal.api.ObservationPackageGateway;
import ngz.meteofrance.observation.internal.service.StationServiceImpl;

public class StationServiceFactory {
    public static StationService create(MeteoFranceClient client) {

        ObservationApi observationApi = new ObservationGateway(client);
        ObservationPackageApi observationPackageApi = new ObservationPackageGateway(client);

        return new StationServiceImpl(observationApi, observationPackageApi);
    }
}
