package ngz.meteofrance.observation.mapper;

import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import ngz.meteofrance.observation.model.ProductData;

public class ProductDataMapper {

    public static ProductData mapFromHttpResponse(HttpResponse<byte[]> response, String url) {

        // use local as it is need to also extract the extension/format
        String filename = extractHeader(response.headers(), "content-disposition", "filename=");

        ProductData file =
                ProductData.builder()
                        .location(url)
                        .fileName(filename)
                        .format(extractFormat(filename))
                        .activityId(extractHeader(response.headers(), "activityid"))
                        .contentType(extractHeader(response.headers(), "content-type"))
                        .date(extractHeader(response.headers(), "date"))
                        .acceptRanges(extractHeader(response.headers(), "accept-ranges"))
                        .content(response.body())
                        .build();
        return file;
    }

    private static String extractFormat(String filename) {
        if (filename == null || !filename.contains(".")) {
            return null; // Added a safe check just in case the filename lacks an extension
        }
        return filename.substring(filename.lastIndexOf('.') + 1); // +1 to not have the '.' char
    }

    private static String extractHeader(
            java.net.http.HttpHeaders headers, String headerName, String prefix) {
        return headers.firstValue(headerName)
                .map(
                        value -> {
                            String extractedValue = value;
                            if (prefix != null && value.contains(prefix)) {
                                extractedValue =
                                        value.substring(value.indexOf(prefix) + prefix.length());
                            }
                            return stripQuotes(extractedValue); // Strip quotes before returning
                        })
                .orElse(null);
    }

    private static String extractHeader(java.net.http.HttpHeaders headers, String headerName) {
        return headers.firstValue(headerName).orElse(null);
    }

    protected static Instant getInstantHeader(
            Map<String, List<String>> headers, String headerName) {
        if (headers.get(headerName) == null || headers.get(headerName).isEmpty()) {
            return null;
        }
        return Instant.parse(headers.get(headerName).getFirst());
    }

    /** Helper method to remove surrounding double or single quotes from a string. */
    private static String stripQuotes(String value) {
        if (value == null || value.trim().isEmpty()) {
            return value;
        }

        String trimmed = value.trim();
        int length = trimmed.length();

        if (length >= 2) {
            char firstChar = trimmed.charAt(0);
            char lastChar = trimmed.charAt(length - 1);

            // Check if it starts and ends with matching quotes
            if ((firstChar == '"' && lastChar == '"') || (firstChar == '\'' && lastChar == '\'')) {
                return trimmed.substring(1, length - 1);
            }
        }
        return trimmed;
    }
}
