package ngz.meteofrance.observation.api;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.model.ProductData;

public interface ObservationApi {

    public ProductData listStations();

    public ProductData getSubHourObservationsByStation(String stationId, Format format);

    public ProductData getSubHourObservationsByStation(
            String stationId, Instant time, Format format);

    public ProductData getHourlyObservationByStation(String stationId, Instant date, Format format);

    public ProductData getHourlyObservationByStation(String stationId, Format format);

    public ProductData listSynopStations(Format format);

    public ProductData getSynopObservations(Format format);

    public ProductData getSynopObservations(String stationId, Format format);

    public ProductData getSynopObservations(
            String stationId, Instant startTime, Instant endTime, Format format);

    public ProductData listBuoys(Format format);

    public ProductData getBuoyObservation(Format format);

    public ProductData getBuoyObservation(List<String> buoyIds, Format format);

    public ProductData getBuoyObservation(
            List<String> buoyIds, Instant startTime, Instant endTime, Format format);
}
