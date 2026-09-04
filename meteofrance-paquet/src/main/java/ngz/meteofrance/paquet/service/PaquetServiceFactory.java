package ngz.meteofrance.paquet.service;

import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.api.PaquetApi;
import ngz.meteofrance.paquet.internal.api.PaquetGateway;
import ngz.meteofrance.paquet.internal.service.PaquetServiceImpl;

public class PaquetServiceFactory {

    public static PaquetService create(MeteoFranceClient client) {

        PaquetApi paquetApi = new PaquetGateway(client);
        return new PaquetServiceImpl(paquetApi);
    }
}
