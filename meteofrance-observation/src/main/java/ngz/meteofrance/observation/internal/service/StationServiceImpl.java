package ngz.meteofrance.observation.internal.service; // french word to avoid key word package

import java.time.Instant;
import java.util.List;
import ngz.meteofrance.observation.api.Format;
import ngz.meteofrance.observation.api.ObservationApi;
import ngz.meteofrance.observation.api.ObservationPackageApi;
import ngz.meteofrance.observation.mapper.StationMapper;
import ngz.meteofrance.observation.model.ProductData;
import ngz.meteofrance.observation.model.Station;
import ngz.meteofrance.observation.service.StationService;

public class StationServiceImpl implements StationService {

    private final ObservationApi observationApi;
    private final ObservationPackageApi observationPackageApi;

    private static final String LIST_STATION_ENDPOINT = "/public/DPPaquetObs/v1/liste-stations";

    public StationServiceImpl(
            ObservationApi observationApi, ObservationPackageApi observationPackageApi) {
        this.observationApi = observationApi;
        this.observationPackageApi = observationPackageApi;
    }

    @Override
    public List<Station> listStations() {

        ProductData productData = this.observationApi.listStations();

        return StationMapper.mapFromRawCsv(productData.getContent());
    }

    @Override
    public ProductData downloadObservationsByDepartmentId(String departmentId, Format format) {
        return this.observationPackageApi.getHourlyObservationByDepartment(departmentId, format);
    }

    @Override
    public ProductData downloadSubHourObservationsByStationId(
            String stationId, Instant time, Format format) {
        return this.observationApi.getSubHourObservationsByStation(stationId, time, format);
    }

    @Override
    public ProductData downloadAllSubHourObservations(Instant time, Format format) {
        return null;
    }

    @Override
    public ProductData downloadAllHourlyObservation(Instant date, Format format) {
        return null;
    }

    @Override
    public ProductData downloadBuoyObservation(
            String buoyId, Instant startTime, Instant endTime, Format format) {
        return null;
    }
}
