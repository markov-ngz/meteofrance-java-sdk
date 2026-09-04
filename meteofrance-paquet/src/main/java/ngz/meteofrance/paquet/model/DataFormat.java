package ngz.meteofrance.paquet.model;

/** The only download format currently supported by the API. */
public enum DataFormat {
    GRIB2;

    public String toApiString() {
        return name().toLowerCase();
    }
}
