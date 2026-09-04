package ngz.meteofrance.paquet.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.paquet.model.ProductData;
import ngz.meteofrance.paquet.model.ProductInfo;

public interface ProductInfoService {

    /**
     * Gets available forecast files for a specific grid and package.
     *
     * @param grid numeric grid identifier
     * @param packageName package identifier
     * @return List of ProductInfo objects representing available forecasts
     */
    List<ProductInfo> getAvailableForecasts(
            String previnum, String model, String grid, String packageName, Instant referenceTime);

    /**
     * Downloads forecast data by direct URL/href.
     *
     * @param href direct URL to the forecast data
     * @return byte array containing the forecast data
     */
    ProductData downloadForecastDataByHref(String href);
}
