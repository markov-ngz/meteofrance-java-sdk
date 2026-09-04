package ngz.meteofrance.paquet.api;

import ngz.meteofrance.paquet.model.ProductData;

/**
 * Product download operations. Exposes both the REST-style and the KVP-style endpoints as separate
 * methods so callers can choose explicitly.
 */
public interface ProductApi {

    ProductData downloadByHref(String href);
}
