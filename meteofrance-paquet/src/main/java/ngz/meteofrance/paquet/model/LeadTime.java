package ngz.meteofrance.paquet.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @param value leadtime value at format "hhhH" (e.g. "001H", "024H").
 */
public record LeadTime(String value) {
    private static final Pattern PATTERN = Pattern.compile("\\d{3}H");

    public LeadTime {
        Objects.requireNonNull(value);
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("LeadTime must match hhhH, got: " + value);
        }
    }
}
