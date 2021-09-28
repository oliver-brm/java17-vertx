package javamodularity.easytext.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class VertxProvider {
    private final static Vertx vertx = Vertx.vertx(new VertxOptions().setPreferNativeTransport(true));

    public static Vertx provider() {
        return vertx;
    }
}
