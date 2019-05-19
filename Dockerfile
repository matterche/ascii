####################################################################################################################
# Builder image to run the tests and build the runtime image.
#
# Layers from this container can be cached and reused for upcoming builds.
#
####################################################################################################################
FROM registry.opensource.zalan.do/stups/openjdk:1.8.0-151-12 as builder

WORKDIR /build

COPY ./sbt ./sbt
COPY ./sbt-dist ./sbt-dist
COPY ./build.sbt ./build.sbt
COPY ./project/build.properties ./project/build.properties
COPY ./project/plugins.sbt ./project/plugins.sbt

RUN echo "Please be patient, downloading the internet..."
RUN ./sbt -sbt-dir ./sbt-dir -ivy ./ivy update

# From this point caching is not possible anymore because we will always add new files here
COPY ./ ./

RUN ./sbt -sbt-dir ./sbt-dir -ivy ./ivy clean test docker:stage

####################################################################################################################
# Runtime image
####################################################################################################################
FROM registry.opensource.zalan.do/stups/openjdk:1.8.0-151-12 as runtime

COPY --from=builder /build/target/docker/stage/opt /opt

WORKDIR /opt/docker

EXPOSE 4444

ENTRYPOINT ["bin/ascii"]
CMD []
