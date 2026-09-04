package ngz.meteofrance.observation.mapper;

import java.util.List;
import ngz.markov.tools.jackson.dataformat.csv.CsvMapper;
import ngz.markov.tools.jackson.dataformat.csv.CsvSchema;
import ngz.meteofrance.observation.model.Station;

public class StationMapper {

    private static final CsvMapper MAPPER = new CsvMapper();

    public static List<Station> mapFromRawCsv(byte[] data) {
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');

        return MAPPER.readerFor(Station.class).with(schema).<Station>readValues(data).readAll();
    }
}
