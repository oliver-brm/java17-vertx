package javamodularity.easytext.pagefetch.impl;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import javamodularity.easytext.pagefetch.WikipediaFetcher;

import java.util.ServiceLoader;

public class WikipediaFetchImpl implements WikipediaFetcher {

    private final WebClient client;

    public WikipediaFetchImpl() {
        var vertx = ServiceLoader.load(Vertx.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("""
                        Where's Vert.x? Seems like there is no module providing Vert.x at the moment. Please provide
                        a JPMS module in the module path, which provides an instance of io.vertx.core.Vertx."""));
        client = WebClient.create(vertx);
    }

    @Override
    public Future<String> getText(String topic) {
        var url = "https://en.wikipedia.org/w/index.php?action=raw&title=" + topic;

        return client.getAbs(url).send()
            .map(HttpResponse::bodyAsString);

    }
}
