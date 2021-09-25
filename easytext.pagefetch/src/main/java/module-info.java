import javamodularity.easytext.pagefetch.WikipediaFetcher;
import javamodularity.easytext.pagefetch.impl.WikipediaFetchImpl;

module easytext.pagefetch {

    requires io.vertx.core;
    requires io.vertx.web.client;
    exports javamodularity.easytext.pagefetch;

    uses io.vertx.core.Vertx;
    provides WikipediaFetcher with WikipediaFetchImpl;

}