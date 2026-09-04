package ngz.meteofrance.paquet.service;

import java.util.List;
import ngz.meteofrance.paquet.model.Grid;
import ngz.meteofrance.paquet.model.Model;

public interface GridService {

    /**
     * Finds grids by model.
     *
     * @param model the AromeModel to filter grids by
     * @return List of AromeGrid objects for the specified model
     */
    List<Grid> findGridsByModel(Model model);

    /**
     * Checks if a specific grid exists.
     *
     * @param grid numeric grid identifier
     * @return true if the grid exists, false otherwise
     */
    boolean exists(String grid);

    /**
     * Finds and returns detailed information about a specific grid.
     *
     * @param grid numeric grid identifier
     * @return AromeGrid object with detailed information
     */
    Grid findGrid(String grid);
}
