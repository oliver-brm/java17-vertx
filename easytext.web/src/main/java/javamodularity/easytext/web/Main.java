package javamodularity.easytext.web;


import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import javamodularity.easytext.algorithm.api.Analyzer;
import javamodularity.easytext.algorithm.api.Preprocessing;
import javamodularity.easytext.pagefetch.WikipediaFetcher;

import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class Main {

    private static final int port = 8080;

    public static void main(String[] args) {

        var startNanos = System.nanoTime();
        var wikipediaFetcher = ServiceLoader.load(WikipediaFetcher.class).findFirst().orElseThrow();
        var analyzers = ServiceLoader.load(Analyzer.class);
        var vertx = ServiceLoader.load(Vertx.class).findFirst().orElseThrow();
        var server = vertx.createHttpServer(new HttpServerOptions().setReusePort(true));

        server.requestHandler(request -> {
            var response = request.response();

            var topic = request.getParam("topic");

            if (topic == null) {
                request.response().setStatusCode(400).end("No topic set");
            } else {
                response.setChunked(true);

                response.putHeader("Content-Type", "text/event-stream");
                response.putHeader("Connection", "keep-alive");
                response.putHeader("Cache-Control", "no-cache");

                wikipediaFetcher.getText(topic)
                        .map(Preprocessing::toSentences)
                        .map(text -> analyzers.stream()
                                .map(ServiceLoader.Provider::get)
                                .map(analyzer -> "%s: %f".formatted(analyzer.getName(), analyzer.analyze(text))))
                        .onSuccess(analysisResults -> response.end(analysisResults.collect(Collectors.joining("\n"))))
                        .onFailure(ex -> {
                            ex.printStackTrace(System.err);
                            response.setStatusCode(500).end(ex.getMessage() + "\n");
                        });
            }

        });

        server.listen(port, result -> {
            if (vertx.isNativeTransportEnabled()) {
                System.out.println("Using native transport");
            }
            System.out.println("""
                    ┏━━━┓━━━━━━━━━━━━━━━┓━━━━━━┏┓━┓
                    ┃┏━━┛━━━━━━━━━━━┏┓┏┓┃━━━━━━┛┗┓┃
                    ┃┗━━┓━━┓━━━┓┓━┏┓┛┃┃┗┛━━┓┓┏┓┓┏┛┃
                    ┃┏━━┛━┓┃━━━┫┃━┃┃━┃┃━━┏┓┃╋╋┛┃┃━┛
                    ┃┗━━┓┗┛┗┓━━┃┗━┛┃┏┛┗┓━┃━┫╋╋┓┃┗┓┓
                    ┗━━━┛━━━┛━━┛━┓┏┛┗━━┛━━━┛┛┗┛┗━┛┛
                    ━━━━━━━━━━━━━┛┃━━━━━━━━━━━━━━━━
                    ━━━━━━━━━━━━━━┛━━━━━━━━━━━━━━━━
                    """);
            System.out.printf("Startup finished after %.2f ms\n", (System.nanoTime() - startNanos) / 1000000000d);
            System.out.printf("Server listening on port %d: %s\n\n", port, result.succeeded());
        });
    }

}
