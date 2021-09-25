module easytext.web {
    requires easytext.pagefetch;
    requires easytext.algorithm.api;
    requires io.vertx.core;

    uses io.vertx.core.Vertx;
    uses javamodularity.easytext.pagefetch.WikipediaFetcher;
    uses javamodularity.easytext.algorithm.api.Analyzer;
}