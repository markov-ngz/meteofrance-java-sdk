package ngz.meteofrance.paquet.service;

import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.api.PaquetApi;
import ngz.meteofrance.paquet.api.ProductApi;
import ngz.meteofrance.paquet.internal.api.PaquetGateway;
import ngz.meteofrance.paquet.internal.api.ProductGateway;
import ngz.meteofrance.paquet.internal.service.ProductInfoServiceImpl;

/** */
public class ProductInfoServiceFactory {

    public static ProductInfoService create(MeteoFranceClient client) {

        PaquetApi paquetApi = new PaquetGateway(client);
        ProductApi productApi = new ProductGateway(client);

        return new ProductInfoServiceImpl(paquetApi, productApi);
    }
}
