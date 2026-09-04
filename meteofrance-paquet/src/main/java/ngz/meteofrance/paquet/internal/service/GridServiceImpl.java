package ngz.meteofrance.paquet.internal.service;

import java.util.List;
import ngz.meteofrance.paquet.api.GridApi;
import ngz.meteofrance.paquet.model.Grid;
import ngz.meteofrance.paquet.model.Model;
import ngz.meteofrance.paquet.service.GridService;

public class GridServiceImpl implements GridService {

    private final GridApi gridApi;

    public GridServiceImpl(GridApi gridApi) {
        this.gridApi = gridApi;
    }

    @Override
    public List<Grid> findGridsByModel(Model model) {
        return List.of();
    }

    @Override
    public boolean exists(String grid) {
        return false;
    }

    @Override
    public Grid findGrid(String grid) {
        return null;
    }
}
