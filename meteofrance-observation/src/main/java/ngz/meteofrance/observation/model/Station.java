package ngz.meteofrance.observation.model;

import lombok.Data;
import ngz.markov.com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Station {

    @JsonProperty("Id_station")
    private String stationId;

    @JsonProperty("Id_omm")
    private String ommId;

    @JsonProperty("Nom_usuel")
    private String commonName;

    @JsonProperty("Latitude")
    private double latitude;

    @JsonProperty("Longitude")
    private double longitude;

    @JsonProperty("Altitude")
    private double altitude;

    @JsonProperty("Date_ouverture")
    private String openingDate;

    @JsonProperty("Pack")
    private String pack;
}
