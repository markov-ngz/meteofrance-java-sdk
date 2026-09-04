package ngz.meteofrance.observation.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.Station;

public interface StationService {

    public List<Station> listStations();

    public ProductData downloadObservationsByDepartmentId(String departmentId, Format format);

    public ProductData downloadSubHourObservationsByStationId(
            String stationId, Instant time, Format format);

    public ProductData downloadAllSubHourObservations(Instant time, Format format);

    public ProductData downloadAllHourlyObservation(Instant date, Format format);

    public ProductData downloadBuoyObservation(
            String buoyId, Instant startTime, Instant endTime, Format format);
}
