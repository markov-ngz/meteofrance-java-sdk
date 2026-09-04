package ngz.meteofrance.paquet.service;

import java.util.List;
import ngz.meteofrance.paquet.model.Model;

/**
 * Service interface for managing and retrieving model descriptions. Models are identified by their
 * names and can be listed or described in detail.
 */
public interface ModelService {

    /**
     * Returns the description of the model with the given name.
     *
     * @param name the name of the model (e.g., "AROME-OM-NCALED")
     * @return a {@link Model} object describing the requested model
     */
    Model describeModel(String name);

    /**
     * Lists all available models.
     *
     * @return an unmodifiable list of all {@link Model} objects
     */
    List<Model> listAllModels();
}
