package ngz.meteofrance.paquet.internal.api;

import java.net.http.HttpResponse;
import ngz.meteofrance.core.client.MeteoFranceClient;
import ngz.meteofrance.paquet.api.ProductApi;
import ngz.meteofrance.paquet.model.ProductData;

/** Implementation of the ProductGateway. */
public class ProductGateway implements ProductApi {
    private final MeteoFranceClient meteoFranceClient;

    public ProductGateway(MeteoFranceClient meteoFranceClient) {
        this.meteoFranceClient = meteoFranceClient;
    }

    @Override
    public ProductData downloadByHref(String href) {
        // 1. Make the request using the provided href
        HttpResponse<byte[]> response =
                meteoFranceClient.request(href, HttpResponse.BodyHandlers.ofByteArray());

        // 2. Extract headers from the response
        String fileName = extractHeader(response.headers(), "content-disposition", "filename=");
        String activityId = extractHeader(response.headers(), "activityid", null);
        String xRequestId = extractHeader(response.headers(), "x-request-id", null);
        String contentType = extractHeader(response.headers(), "content-type", null);
        String date = extractHeader(response.headers(), "date", null);
        String acceptRanges = extractHeader(response.headers(), "accept-ranges", null);

        // 3. Create and return ProductData with the binary content and metadata
        // Note: For downloadByHref, we don't have grid/package/leadTime info, so we use
        // null/empty values
        return new ProductData(
                href,
                fileName,
                activityId,
                xRequestId,
                contentType,
                date,
                acceptRanges,
                response.body());
    }

    /**
     * Helper method to extract header values from HTTP response headers.
     *
     * @param headers the HTTP response headers
     * @param headerName the name of the header to extract
     * @param prefix optional prefix to remove from the header value (e.g., "filename=")
     * @return the extracted header value, or null if not found
     */
    private String extractHeader(
            java.net.http.HttpHeaders headers, String headerName, String prefix) {
        return headers.firstValue(headerName)
                .map(
                        value -> {
                            if (prefix != null && value.contains(prefix)) {
                                return value.substring(value.indexOf(prefix) + prefix.length());
                            }
                            return value;
                        })
                .orElse(null);
    }
}
