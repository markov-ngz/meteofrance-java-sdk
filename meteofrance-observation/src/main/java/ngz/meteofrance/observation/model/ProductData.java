package ngz.meteofrance.observation.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
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
    private String format;
}
