package ngz.meteofrance.paquet.api;

import java.time.Instant;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;

/** Paquet-level operations: listing and describing packages for a given grid. */
public interface PaquetApi {

    /**
     * Lists all package types available for a given grid.
     *
     * @param grid numeric grid identifier
     * @return raw JSON string containing the package list
     */
    MeteoFranceGridResponse listPaquets(String previnum, String model, String grid);

    MeteoFranceGridResponse describePaquet(
            String previnum, String model, String grid, String packageName);

    /**
     * Describes a specific package and its available products. When {@code referenceTime} is
     * provided the response is filtered to that run.
     *
     * @param previnum prevision number
     * @param model model of api ( arome-om , arpege .. )
     * @param grid numeric grid identifier ( 0.025
     * @param packageName package identifier (e.g. "SP1", "HP1")
     * @param referenceTime optional forecast run timestamp; pass {@code null} for latest
     * @return raw JSON string describing the package
     */
    MeteoFranceGridResponse listProductInfo(
            String previnum, String model, String grid, String packageName, Instant referenceTime);
}
