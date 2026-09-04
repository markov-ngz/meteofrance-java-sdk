package ngz.meteofrance.observation.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ngz.meteofrance.observation.model.Station;
import org.junit.jupiter.api.Test;

public class StationMapperTest {

    @Test
    void shouldMapProperly() throws Exception {
        //
        byte[] data = Files.readAllBytes(Path.of("src/test/resources/observation/stations.csv"));

        List<Station> stations = StationMapper.mapFromRawCsv(data);
        assertThat(stations).hasSize(7);

        assertThat(stations.getFirst())
                .extracting(
                        Station::getStationId,
                        Station::getOmmId,
                        Station::getCommonName,
                        Station::getLatitude,
                        Station::getLongitude,
                        Station::getAltitude,
                        Station::getOpeningDate,
                        Station::getPack)
                .containsExactly(
                        "01014002",
                        "",
                        "ARBENT",
                        46.278167,
                        5.669000,
                        534.0,
                        "2003-10-01",
                        "RADOME");
    }
}
