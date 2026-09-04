package ngz.meteofrance.paquet.internal.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.paquet.api.PaquetApi;
import ngz.meteofrance.paquet.api.ProductApi;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.mapper.ProductInfoMapper;
import ngz.meteofrance.paquet.model.ProductData;
import ngz.meteofrance.paquet.model.ProductInfo;
import ngz.meteofrance.paquet.service.ProductInfoService;

public class ProductInfoServiceImpl implements ProductInfoService {

    private final PaquetApi paquetApi;
    private final ProductApi productApi;

    public ProductInfoServiceImpl(PaquetApi paquetApi, ProductApi productApi) {
        this.paquetApi = paquetApi;
        this.productApi = productApi;
    }

    @Override
    public List<ProductInfo> getAvailableForecasts(
            String previnum, String model, String grid, String packageName, Instant referenceTime) {

        MeteoFranceGridResponse response =
                this.paquetApi.listProductInfo(previnum, model, grid, packageName, referenceTime);

        List<ProductInfo> productInfos =
                ProductInfoMapper.mapFromMeteoFranceGridResponse(
                        response, previnum, model, grid, packageName);

        return productInfos;
    }

    @Override
    public ProductData downloadForecastDataByHref(String href) {

        ProductData productData = this.productApi.downloadByHref(href);

        return productData;
    }
}
