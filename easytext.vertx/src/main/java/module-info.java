module easytext.vertx {
    requires transitive io.vertx.core;
    provides io.vertx.core.Vertx with javamodularity.easytext.vertx.VertxProvider;

}