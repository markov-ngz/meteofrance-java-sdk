package ngz.meteofrance.paquet.mapper;

import java.util.List;
import java.util.Optional;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;

public class MeteoFranceGridResponseFixture {

    public static MeteoFranceGridResponse.MeteoFranceGridResponseBuilder
            validPaquetReferenceTimes() {

        MeteoFranceGridResponse.Link selfLink =
                MeteoFranceGridResponse.Link.builder()
                        .href(
                                "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1")
                        .rel("self")
                        .type("application/json")
                        .title("Ce document")
                        .referenceTime(Optional.empty())
                        .time(Optional.empty())
                        .insertTime(Optional.empty())
                        .build();

        MeteoFranceGridResponse.Link dataLink =
                MeteoFranceGridResponse.Link.builder()
                        .href(
                                "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1?&referencetime=2026-06-07T06:00:00Z")
                        .rel("http://www.opengis.net/def/rel/ogc/1.0/data")
                        .type("application/json")
                        .title("Réseau du 2026-06-07 à 06:00:00 UTC")
                        .referenceTime(Optional.of("2026-06-07T06:00:00Z"))
                        .time(Optional.empty())
                        .insertTime(Optional.empty())
                        .build();

        MeteoFranceGridResponse.Link dataLink2 =
                MeteoFranceGridResponse.Link.builder()
                        .href(
                                "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1?&referencetime=2026-06-07T12:00:00Z")
                        .rel("http://www.opengis.net/def/rel/ogc/1.0/data")
                        .type("application/json")
                        .title("Réseau du 2026-06-07 à 12:00:00 UTC")
                        .referenceTime(Optional.of("2026-06-07T12:00:00Z"))
                        .time(Optional.empty())
                        .insertTime(Optional.empty())
                        .build();
        MeteoFranceGridResponse.Link dataLink3 =
                MeteoFranceGridResponse.Link.builder()
                        .href(
                                "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1?&referencetime=2026-06-07T18:00:00Z")
                        .rel("http://www.opengis.net/def/rel/ogc/1.0/data")
                        .type("application/json")
                        .title("Réseau du 2026-06-07 à 18:00:00 UTC")
                        .referenceTime(Optional.of("2026-06-07T18:00:00Z"))
                        .time(Optional.empty())
                        .insertTime(Optional.empty())
                        .build();
        return MeteoFranceGridResponse.builder()
                .title(
                        "Paramêtres courants en niveaux hauteur disponibles pour le modèle AROME-OM-NCALED 0.025")
                .description("T, HU, U, V, DD, FF, P sur 24 niveaux (20 à 3000m)")
                .attribution("Source : Météo-France")
                .links(List.of(selfLink, dataLink, dataLink2, dataLink3));
    }

    public static MeteoFranceGridResponse.MeteoFranceGridResponseBuilder validAvailableForecast() {

        MeteoFranceGridResponse.Link selfLink =
                MeteoFranceGridResponse.Link.builder()
                        .href(
                                "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1?&referencetime=2026-06-10T00:00:00Z")
                        .rel("self")
                        .type("application/json")
                        .title("Ce document")
                        .referenceTime(Optional.empty())
                        .time(Optional.empty())
                        .insertTime(Optional.empty())
                        .build();
        return MeteoFranceGridResponse.builder()
                .title("Réseau du 2026-06-10 à 00:00:00 UTC pour le modèle AROME-OM-NCALED 0.025")
                .description(
                        "Liste des échéances et formats des paquets disponibles au téléchargement pour le réseau du 2026-06-10 à 00:00:00 UTC.")
                .attribution("Source : Météo-France")
                .links(
                        List.of(
                                selfLink,
                                linkLeadTime("000H", "2026-06-10T06:23:55Z"),
                                linkLeadTime("001H", "2026-06-10T06:25:04Z"),
                                linkLeadTime("002H", "2026-06-10T06:26:01Z"),
                                linkLeadTime("003H", "2026-06-10T06:26:40Z")));
    }

    private static MeteoFranceGridResponse.Link linkLeadTime(String time, String insertTime) {

        String referenceTime = "2026-06-10T00:00:00Z";

        return MeteoFranceGridResponse.Link.builder()
                .href(
                        "https://public-api.meteofrance.fr/previnum/DPPaquetAROME-OM/models/AROME-OM-NCALED/grids/0.025/packages/HP1/productOMNC"
                                + "?&referencetime="
                                + referenceTime
                                + "&time="
                                + time
                                + "&format=grib2")
                .rel("http://www.opengis.net/def/rel/ogc/1.0/data")
                .type("application/octet-stream")
                .title(
                        String.format(
                                "Échéance %s du réseau du 2026-06-10 à 00:00:00 UTC au format grib2",
                                time))
                .referenceTime(Optional.of(referenceTime))
                .time(Optional.of(time))
                .insertTime(Optional.of(insertTime))
                .build();
    }
}
