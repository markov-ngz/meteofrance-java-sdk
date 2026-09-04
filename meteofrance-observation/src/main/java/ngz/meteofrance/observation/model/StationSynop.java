package ngz.meteofrance.observation.model;

import lombok.Data;
import ngz.markov.com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class StationSynop {

    private String name;

    @JsonProperty("geo_id_wmo")
    private String geoIdWmo;

    @JsonProperty("geo_id_wigos")
    private String geoIdWigos;

    private double lat;
    private double lon;
}
