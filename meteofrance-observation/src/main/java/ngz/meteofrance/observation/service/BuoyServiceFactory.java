package ngz.meteofrance.observation.service;

import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.internal.api.ObservationGateway;
import ngz.meteofrance.observation.internal.service.BuoyServiceImpl;

public class BuoyServiceFactory {
    public static BuoyService create(MeteoFranceClient client) {

        ObservationApi observationApi = new ObservationGateway(client);

        return new BuoyServiceImpl(observationApi);
    }
}
