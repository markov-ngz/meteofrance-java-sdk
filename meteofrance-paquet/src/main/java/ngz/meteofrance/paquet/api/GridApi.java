package ngz.meteofrance.paquet.api;

import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;

/** Grid-level operations: listing and describing grids. */
public interface GridApi {

    /**
     * Lists all grids available for the model.
     *
     * @return raw JSON string containing the grid list
     */
    MeteoFranceGridResponse listGrids(String previnum, String model);

    /**
     * Returns the description of a specific grid.
     *
     * @param grid numeric grid identifier
     * @return raw JSON string describing the grid
     */
    MeteoFranceGridResponse describeGrid(String previnum, String model, String grid);
}
