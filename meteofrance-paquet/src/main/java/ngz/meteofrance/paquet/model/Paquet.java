package ngz.meteofrance.paquet.model;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a package in the AROME-OM-NCALED model. Each grid has multiple packages (e.g., SP1,
 * HP1) which contain forecast data.
 */
@Data
@Builder
public class Paquet {

    private String id;

    private String previnum;
    private String model;
    private String gridId;

    private String title;
    private String description;
    private String type; // e.g., "SP", "HP" to group the package
    private String link; // SP1 , SP2 ...
    private String attribution;
    private List<Instant> referenceTimes;
}
