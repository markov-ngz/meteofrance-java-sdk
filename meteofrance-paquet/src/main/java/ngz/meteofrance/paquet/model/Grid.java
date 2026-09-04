package ngz.meteofrance.paquet.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a grid in the AROME-OM-NCALED model. Each grid covers a specific geographic area and
 * has associated packages.
 */
@Data
@Builder
public class Grid {

    private String id;

    private String previnum;

    private String title;
    private String description;
    private String endpoint;
    private String attribution;
}
