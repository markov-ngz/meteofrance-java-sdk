package ngz.meteofrance.observation.service;

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.StationSynop;

public interface BuoyService {

    public List<StationSynop> listBuoys();

    public ProductData getBuoyObservation(Format format);

    public ProductData getBuoyObservation(List<String> buoyIds, Format format);

    public ProductData getBuoyObservation(
            List<String> buoyIds, Instant startTime, Instant endTime, Format format);
}
