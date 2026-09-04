package ngz.meteofrance.core.client;

import java.net.http.HttpResponse;

/** Client interface for making requests to the Météo-France API. */
public interface MeteoFranceClient {

    <T> HttpResponse<T> request(String endpoint, HttpResponse.BodyHandler<T> responseHandler);
}
