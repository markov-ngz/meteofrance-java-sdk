package ngz.meteofrance.observation.api;

import java.time.Instant;
import ngz.meteofrance.observation.model.ProductData;

public interface ObservationPackageApi {

    public ProductData listStations();

    public ProductData getSubHourObservationsByStation(String stationId, Format format);

    public ProductData getHourlyObservationByDepartment(String departmentId, Format format);

    public ProductData getSubHourObservation(Instant time, Format format);

    public ProductData getHourlyObservation(Instant date, Format format);
}
