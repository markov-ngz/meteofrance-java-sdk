package ngz.opengis.wcs.service;

import ngz.opengis.wcs.exception.WcsException;
import ngz.opengis.wcs.model.Capabilities;
import ngz.opengis.wcs.model.Coverage;

public interface WcsService {

    public Capabilities getCapabilities() throws WcsException;

    public Coverage describeCoverage(String coverageId) throws WcsException;

    public byte[] getCoverage(String coverageUrl) throws WcsException;
}
