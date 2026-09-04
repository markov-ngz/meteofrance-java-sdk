package ngz.meteofrance.wcs.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryParameterUtil {

    public static String encodeQueryParams(Map<String, String> map) {

        List<String> params = new ArrayList<>();

        map.forEach((k, v) -> params.add(encodeParam(k, v)));

        return String.join("&", params);
    }

    private static String encodeParam(String key, String value) {
        return encode(key) + "=" + encode(value);
    }

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
