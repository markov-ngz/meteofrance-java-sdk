package ngz.meteofrance.observation.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.StationSynop;

public interface SynopStationService {

    public List<StationSynop> listSynopStations();

    public ProductData getSynopObservations(Format format);

    public ProductData getSynopObservations(String stationId, Format format);

    public ProductData getSynopObservations(
            String stationId, Instant startTime, Instant endTime, Format format);
}
