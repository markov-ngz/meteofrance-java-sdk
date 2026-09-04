package ngz.meteofrance.paquet.internal.service;

import java.util.List;
import ngz.meteofrance.paquet.api.ModelApi;
import ngz.meteofrance.paquet.model.Model;
import ngz.meteofrance.paquet.service.ModelService;

public class ModelServiceImpl implements ModelService {

    private final ModelApi modelApi;

    public ModelServiceImpl(ModelApi modelApi) {
        this.modelApi = modelApi;
    }

    @Override
    public Model describeModel(String name) {
        return null;
    }

    @Override
    public List<Model> listAllModels() {
        return List.of();
    }
}
