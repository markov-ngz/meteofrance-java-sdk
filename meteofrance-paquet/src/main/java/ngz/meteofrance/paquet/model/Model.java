package ngz.meteofrance.paquet.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the AROME-OM-NCALED forecast model. Contains metadata about the model and its
 * capabilities.
 */
@Data
@Builder
public class Model {

    private String id; // would be the last part of the endpoint AROME-OM-NCALED
    private String title;
    private String description;
    private String endpoint;
    private String attribution;

    private String previnum;
}
