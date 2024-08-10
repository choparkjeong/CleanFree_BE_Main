FROM bellsoft/liberica-openjdk-alpine:17 as build
WORKDIR /workspace/app

# Copy the built JAR file
COPY build/libs/*.jar .

ENTRYPOINT ["java","-jar","cleanfree_main-0.0.1-SNAPSHOT.jar"]
