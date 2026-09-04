package ngz.meteofrance.observation.mapper;

import java.util.List;
import ngz.markov.tools.jackson.core.type.TypeReference;
import ngz.markov.tools.jackson.databind.ObjectMapper;
import ngz.meteofrance.observation.model.StationSynop;

public class StationSynopMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<StationSynop> mapFromRawJson(byte[] data) {
        return MAPPER.readValue(data, new TypeReference<List<StationSynop>>() {});
    }
}
