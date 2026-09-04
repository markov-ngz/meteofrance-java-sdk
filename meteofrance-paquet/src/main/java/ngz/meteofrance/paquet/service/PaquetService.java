package ngz.meteofrance.paquet.service;

import java.util.List;
import ngz.meteofrance.paquet.model.Paquet;

public interface PaquetService {

    /**
     * Lists all package types available for a given grid.
     *
     * @param grid numeric grid identifier
     * @return List of AromePaquet objects available for the grid
     */
    List<Paquet> listPaquets(String previnum, String model, String grid);

    /**
     * Describes a specific package and its available products.
     *
     * @param grid numeric grid identifier
     * @param packageName package identifier (e.g. "SP1", "HP1")
     * @return AromePaquet object with detailed information
     */
    Paquet describePaquet(String previnum, String model, String grid, String packageName);
}
