package ngz.opengis.wcs.parser;

import ngz.opengis.wcs.exception.WcsException;
import ngz.opengis.wcs.exception.WcsParsingException;

/**
 * Generic interface for parsing XML content into DTO objects.
 *
 * @param <T> the type of DTO object to parse the XML into
 */
public interface WcsResponseParser<T> {

    /**
     * Parses the given XML bytes into the specified DTO type.
     *
     * @param xmlBytes the XML content as byte array
     * @return the parsed DTO object
     * @throws WcsParsingException if parsing fails
     */
    T parse(byte[] xmlBytes) throws WcsException;
}
