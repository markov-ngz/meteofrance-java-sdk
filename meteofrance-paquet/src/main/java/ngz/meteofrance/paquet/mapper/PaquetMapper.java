package ngz.meteofrance.paquet.mapper;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.model.Paquet;

public class PaquetMapper {
    public static Paquet mapFromMeteoFranceGridResponse(
            MeteoFranceGridResponse response,
            String previnum,
            String model,
            String gridId,
            String packageName) {

        List<MeteoFranceGridResponse.Link> links = response.getLinks();

        Paquet paquet =
                Paquet.builder()
                        .id(packageName)
                        .previnum(previnum)
                        .gridId(gridId)
                        .title(response.getTitle())
                        .link(extractSelfLink(links))
                        .referenceTimes(extractReferenceTimesFromLinks(links))
                        .build();

        return paquet;
    }

    private static String extractSelfLink(List<MeteoFranceGridResponse.Link> links) {
        return links.stream()
                .filter(link -> "self".equals(link.getRel()))
                .map(MeteoFranceGridResponse.Link::getHref)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("No valid self link found in response"));
    }

    private static List<Instant> extractReferenceTimesFromLinks(
            List<MeteoFranceGridResponse.Link> links) {
        return links.stream()
                .filter(link -> !link.getRel().equals("self"))
                .map(
                        link -> {
                            String referenceTime =
                                    extractQueryParameter(link.getHref(), "referencetime");

                            if (referenceTime == null) {
                                throw new IllegalArgumentException(
                                        "Missing 'referencetime' query parameter in href: "
                                                + link.getHref());
                            }

                            return Instant.parse(referenceTime);
                        })
                .toList();
    }

    public static String extractQueryParameter(String href, String queryParam) {
        URI uri = URI.create(href);

        String query = uri.getRawQuery();
        if (query == null || query.isBlank()) {
            return null;
        }

        // for each query param
        for (String pair : query.split("&")) {

            // position of "="
            int idx = pair.indexOf('=');

            String name;
            String value;

            if (idx >= 0) {
                name = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                value = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);

                if (queryParam.equals(name)) {
                    return value;
                }
            }
        }

        return null;
    }
}
