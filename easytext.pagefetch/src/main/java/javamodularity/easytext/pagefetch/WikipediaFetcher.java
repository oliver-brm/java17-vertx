package javamodularity.easytext.pagefetch;

import io.vertx.core.Future;


public interface WikipediaFetcher {
    Future<String> getText(String topic);
}
