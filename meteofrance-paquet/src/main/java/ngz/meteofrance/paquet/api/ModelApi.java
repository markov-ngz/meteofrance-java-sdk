package ngz.meteofrance.paquet.api;

import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;

public interface ModelApi {

    /**
     * Returns the raw JSON description of the AROME-OM-NCALED model.
     *
     * @return model description as a JSON string
     */
    MeteoFranceGridResponse describeModel(String previnum, String model);
}
