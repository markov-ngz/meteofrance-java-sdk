package ngz.meteofrance.paquet.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.model.ProductInfo;

public class ProductInfoMapper {

    private static final Pattern FORECAST_TIME_PATTERN = Pattern.compile("^(\\d{3})H$");

    public static List<ProductInfo> mapFromMeteoFranceGridResponse(
            MeteoFranceGridResponse response,
            String previnum,
            String model,
            String grid,
            String packageName) {

        ProductInfo.ProductInfoBuilder builder =
                ProductInfo.builder()
                        .previnum(previnum)
                        .model(model)
                        .gridId(grid)
                        .packageName(packageName);

        List<ProductInfo> productInfos = new ArrayList<>();

        for (MeteoFranceGridResponse.Link link : response.getLinks()) {
            if (skipLink(link)) {
                continue;
            }

            ProductInfo productInfo =
                    builder.title(link.getTitle())
                            .insertTime(link.getInsertTime().map(Instant::parse).orElse(null))
                            .referenceTime(link.getReferenceTime().map(Instant::parse).orElse(null))
                            .time(parseTime(link.getTime()))
                            .location(link.getHref())
                            .dataFormat(
                                    PaquetMapper.extractQueryParameter(link.getHref(), "format"))
                            .build();

            productInfos.add(productInfo);
        }

        return productInfos;
    }

    private static boolean skipLink(MeteoFranceGridResponse.Link link) {

        if (link.getRel().equals("self")) {
            return true;
        }

        if (link.getTime().isPresent()) {
            String time = link.getTime().get();
            return time.equals("000H999H");
        }

        return false;
    }

    private static int parseTime(Optional<String> value) {
        String time =
                value.orElseThrow(() -> new IllegalArgumentException("Missing forecast time"));

        if (!time.matches("\\d{3}H")) {
            throw new IllegalArgumentException("Invalid forecast time format: " + time);
        }

        return Integer.parseInt(time.substring(0, 3));
    }
}
