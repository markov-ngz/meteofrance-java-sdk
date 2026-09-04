/** Module to interact with meteofrance observation API endpoints */
module ngz.meteofrance.observation {
    requires ngz.meteofrance.core;
    requires markov.shaded.jackson;
    requires lombok;
    requires java.net.http;
    requires org.apache.httpcomponents.core5.httpcore5;

    exports ngz.meteofrance.observation.api;
    exports ngz.meteofrance.observation.service;
    exports ngz.meteofrance.observation.model;
    exports ngz.meteofrance.observation.mapper;
}
