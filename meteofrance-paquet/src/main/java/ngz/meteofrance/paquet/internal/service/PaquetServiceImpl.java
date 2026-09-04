package ngz.meteofrance.paquet.internal.service;

import java.util.ArrayList;
import java.util.List;
import ngz.meteofrance.paquet.api.PaquetApi;
import ngz.meteofrance.paquet.dto.MeteoFranceGridResponse;
import ngz.meteofrance.paquet.mapper.PaquetMapper;
import ngz.meteofrance.paquet.model.Paquet;
import ngz.meteofrance.paquet.service.PaquetService;

public class PaquetServiceImpl implements PaquetService {
    private final PaquetApi paquetApi;

    public PaquetServiceImpl(PaquetApi paquetApi) {
        this.paquetApi = paquetApi;
    }

    @Override
    public List<Paquet> listPaquets(String previnum, String model, String grid) {
        // Get the list of packages
        MeteoFranceGridResponse response = this.paquetApi.listPaquets(previnum, model, grid);

        List<Paquet> paquets = new ArrayList<>();

        return paquets;
    }

    @Override
    public Paquet describePaquet(String previnum, String model, String grid, String packageName) {

        MeteoFranceGridResponse response =
                this.paquetApi.describePaquet(previnum, model, grid, packageName);

        return PaquetMapper.mapFromMeteoFranceGridResponse(
                response, previnum, model, grid, packageName);
    }
}
