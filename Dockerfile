# Build stage
FROM bellsoft/liberica-openjdk-alpine:17 as build
WORKDIR /workspace/app

# Copy the built JAR file
COPY build/libs/*.jar app.jar

# Unpack the built application
RUN java -Djarmode=layertools -jar app.jar extract --destination target/extracted

# Runtime stage
FROM bellsoft/liberica-openjre-alpine:17
WORKDIR /app

# Copy over the unpacked application
COPY --from=build /workspace/app/target/extracted/dependencies/ ./
COPY --from=build /workspace/app/target/extracted/spring-boot-loader/ ./
COPY --from=build /workspace/app/target/extracted/snapshot-dependencies/ ./
COPY --from=build /workspace/app/target/extracted/application/ ./

# Clean up unnecessary files and caches
RUN rm -rf /tmp/* /var/cache/apk/*

# Set the entry point
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
