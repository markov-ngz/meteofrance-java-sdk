package ngz.meteofrance.observation.service;

import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.internal.api.ObservationGateway;
import ngz.meteofrance.observation.internal.service.SynopStationServiceimpl;

public class SynopStationServiceFactory {
    public static SynopStationService create(MeteoFranceClient client) {

        ObservationApi observationApi = new ObservationGateway(client);

        return new SynopStationServiceimpl(observationApi) {};
    }
}
