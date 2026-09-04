package ngz.meteofrance.paquet.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a forecast from the AROME-OM-NCALED model. Each forecast is associated with a specific
 * reference time and lead time.
 */
@Data
@Builder
public class ProductInfo {

    private String previnum;
    private String model;
    private String gridId;
    private String packageName;
    private Instant referenceTime;
    private int time;
    private String title;
    private String dataFormat; // grib2
    private String location; // location
    private Instant insertTime;
}
