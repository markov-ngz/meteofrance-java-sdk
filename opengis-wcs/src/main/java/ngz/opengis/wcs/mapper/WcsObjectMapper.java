package ngz.opengis.wcs.mapper;

import ngz.opengis.wcs.exception.WcsMappingException;

/**
 * Generic interface for mapping DTO objects to domain model objects.
 *
 * @param <D> the DTO type
 * @param <M> the domain model type
 */
public interface WcsObjectMapper<D, M> {

    /**
     * Maps a single DTO object to its corresponding domain model object.
     *
     * @param dto the DTO object to map
     * @return the mapped domain model object
     * @throws WcsMappingException if mapping fails
     */
    M mapFromDto(D dto) throws WcsMappingException;
}
