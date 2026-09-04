package ngz.meteofrance.observation.internal.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.mapper.StationSynopMapper;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.StationSynop;
import ngz.meteofrance.observation.service.BuoyService;

public class BuoyServiceImpl implements BuoyService {

    private final ObservationApi observationApi;

    public BuoyServiceImpl(ObservationApi observationApi) {
        this.observationApi = observationApi;
    }

    @Override
    public List<StationSynop> listBuoys() {
        ProductData productData = this.observationApi.listBuoys(Format.json);

        return StationSynopMapper.mapFromRawJson(productData.getContent());
    }

    @Override
    public ProductData getBuoyObservation(Format format) {
        return this.observationApi.getBuoyObservation(format);
    }

    @Override
    public ProductData getBuoyObservation(List<String> buoyIds, Format format) {
        return this.observationApi.getBuoyObservation(buoyIds, format);
    }

    @Override
    public ProductData getBuoyObservation(
            List<String> buoyIds, Instant startTime, Instant endTime, Format format) {
        return this.observationApi.getBuoyObservation(buoyIds, startTime, endTime, format);
    }
}
