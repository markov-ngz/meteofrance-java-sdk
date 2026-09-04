/** Meteo France paquet API SDK. */
module ngz.meteofrance.paquet {
    requires lombok;
    requires ngz.meteofrance.core;
    requires com.fasterxml.jackson.annotation;
    requires java.net.http;
    requires tools.jackson.databind;

    exports ngz.meteofrance.paquet.api;
    exports ngz.meteofrance.paquet.dto;
    exports ngz.meteofrance.paquet.model;
    exports ngz.meteofrance.paquet.service;
}
