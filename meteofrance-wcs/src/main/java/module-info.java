/** Meteo France WCS API SDK. */
module ngz.meteofrance.wcs {
    requires lombok;
    requires ngz.opengis.wcs;
    requires java.net.http;
    requires ngz.meteofrance.core;

    exports ngz.meteofrance.wcs.factory;
    exports ngz.meteofrance.wcs.model;
    exports ngz.meteofrance.wcs.service;
    exports ngz.meteofrance.wcs.tools;
    exports ngz.meteofrance.wcs.utils;
}
