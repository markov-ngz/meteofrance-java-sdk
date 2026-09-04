package ngz.meteofrance.paquet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Raw binary payload of a downloaded GRIB2 product, plus its grid/package metadata. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductData {

    // Specification for a request
    private String location;

    // headers received
    private String fileName;
    private String activityId;
    private String xRequestId;
    private String contentType;
    private String date;
    private String acceptRanges;

    // file content
    private byte[] content;
}
