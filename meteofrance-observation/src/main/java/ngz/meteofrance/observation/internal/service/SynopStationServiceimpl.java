package ngz.meteofrance.observation.internal.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.mapper.StationSynopMapper;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.StationSynop;
import ngz.meteofrance.observation.service.SynopStationService;

public class SynopStationServiceimpl implements SynopStationService {

    private final ObservationApi observationApi;

    public SynopStationServiceimpl(ObservationApi observationApi) {
        this.observationApi = observationApi;
    }

    @Override
    public List<StationSynop> listSynopStations() {
        ProductData productData = this.observationApi.listSynopStations(Format.json);

        return StationSynopMapper.mapFromRawJson(productData.getContent());
    }

    @Override
    public ProductData getSynopObservations(Format format) {
        return this.observationApi.getSynopObservations(format);
    }

    @Override
    public ProductData getSynopObservations(String stationId, Format format) {
        return this.observationApi.getSynopObservations(stationId, format);
    }

    @Override
    public ProductData getSynopObservations(
            String stationId, Instant startTime, Instant endTime, Format format) {
        return this.observationApi.getSynopObservations(stationId, startTime, endTime, format);
    }
}
