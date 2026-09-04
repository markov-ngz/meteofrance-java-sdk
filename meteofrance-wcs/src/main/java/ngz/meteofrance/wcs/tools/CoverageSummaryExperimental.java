package ngz.meteofrance.wcs.tools;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoverageSummaryExperimental {

    private static final Pattern REGEX_PATTERN =
            Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}\\.\\d{2}\\.\\d{2}Z");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ssX");

    public static Instant inferReferenceTimeFromCoverageId(String coverageId) {
        if (coverageId == null || coverageId.isBlank()) {
            throw new IllegalArgumentException("coverageId must not be null or blank");
        }

        Matcher matcher = REGEX_PATTERN.matcher(coverageId);

        if (!matcher.find()) {
            throw new IllegalArgumentException("No timestamp found in coverageId: " + coverageId);
        }

        String timeString = matcher.group();

        try {
            return Instant.from(TIME_FORMATTER.parse(timeString));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Extracted string is not a valid timestamp: " + timeString, e);
        }
    }
}
