/** Core Meteofrance module to handle authentication and api calls */
module ngz.meteofrance.core {
    requires org.slf4j;
    requires java.net.http;
    requires markov.shaded.jackson;

    exports ngz.meteofrance.core.client;
    exports ngz.meteofrance.core.exception;
    exports ngz.meteofrance.core.auth;
}
