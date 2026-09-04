/** Opengis WCS parser */
module ngz.opengis.wcs {
    requires lombok;
    requires jakarta.xml.bind;
    requires jakarta.validation;

    exports ngz.opengis.wcs.dto;
    exports ngz.opengis.wcs.exception;
    exports ngz.opengis.wcs.mapper;
    exports ngz.opengis.wcs.model;
    exports ngz.opengis.wcs.parser;
    exports ngz.opengis.wcs.service;

    opens ngz.opengis.wcs.dto to
            jakarta.xml.bind;
}
