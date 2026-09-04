package ngz.meteofrance.observation.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ngz.meteofrance.observation.model.StationSynop;
import org.junit.jupiter.api.Test;

public class StationSynopMapperTest {

    @Test
    void shouldMapProperly() throws Exception {

        byte[] data =
                Files.readAllBytes(Path.of("src/test/resources/observation/station_synop.json"));

        List<StationSynop> stationSynops = StationSynopMapper.mapFromRawJson(data);

        assertThat(stationSynops.size()).isEqualTo(9);

        assertThat(stationSynops.getFirst())
                .extracting(
                        StationSynop::getName,
                        StationSynop::getGeoIdWmo,
                        StationSynop::getGeoIdWigos,
                        StationSynop::getLat,
                        StationSynop::getLon)
                .containsExactly("BOUEE_AZUR", "6100001", "0-22000-16-6100001", 43.36, 7.83);
    }
}
