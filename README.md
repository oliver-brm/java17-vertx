Example code of a modular Java 17 web app using Vert.x.

This code was forked from [Paul Bakker's repo](https://github.com/java9-modularity/java9-vertx), which was written
together with the blog post [Java 9 modules with Vertx](http://paulbakker.io/java/java9-vertx/).

The project can be built and run using Gradle.

## Notable Changes

  1. Upgraded to Java 17
  2. Upgraded to Gradle 7.2
  3. Upgraded to Vert.x 4.1.4
  4. Replaced RxJava2 by Vert.x 4.x Futures
  5. Added the `org.beryx.jlink` Gradle Plugin and configured it, so that we can build custom JVM runtimes
