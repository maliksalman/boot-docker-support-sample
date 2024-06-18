# boot-docker-support-sample

A sample spring-boot app that demonstrates newly added [docker-compose support](https://docs.spring.io/spring-boot/reference/features/dev-services.html#features.dev-services.docker-compose) to create and configure service containers for local development. It also shows usage of [test-containers](https://java.testcontainers.org/) for automated testing.

## Pre-requisites

- JDK 21+
- Docker

## Running

```
./mvnw clean spring-boot:run
```

This will start the application while waiting for a containers specified in [`compose.yaml`](compose.yaml) to be started. Our configuration configures a *MySQL* database container but most common database and messaging related containers are supported. More details are available in the [official spring-boot documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.docker-compose.service-connections). Not only will the containers be started and stopped with the application start/stop but connection details like ports, credentials, and connection URL are also properly injected into the application upon startup.

Under the covers `docker compose up -d` is invoked when the application is stating. And `docker compose stop` is called when the application is shutting down.

The application will behave like this even when running it through your IDE.

## Running the tests

```
./mvnw clean test
```

This will run the tests and will start/stop a *MySQL* container populated with the test data that is under developer control. This test container is not the same as the one started while running the main application locally - it is a dedicated container for testing. See [HeroRepositoryTests.java](src/test/java/com/example/dockerdemo/HeroRepositoryTests.java) and the configuration class it imports to see how the *MySQL* container for testing was configured.

## Runtime (outside of DEV lifecycle)

After the application is bundled as a self-executing JAR, the `compose.yaml` file is no longer acted upon because the docker-compose capability is only supposed to aid during development. If the application needs to connect to a database in a non-local environment, we can rely on the normal spring properties mechanism to supply DB connection information.

## Improving Startup Times

Throughout all of these steps, we will simulate running in a non-dev environment by running a *MySQL* instance - since the automatic *docker-compose* support is not involved here. Start that external *MySQL* manually by running:

```
docker compose -f externaldb.yaml up -d
```

Also, compile a self-executing JAR so we can use it in subsequent steps

```
./mvnw clean package
```

Notice how long it took to compile/package our application. Majority of the time is spent in running our unit tests.

### Taking base measurements

We start the application by running [`bin/run-jar.sh`](bin/run-jar.sh). Notice the startup time. If the tail end of log output was the following:

```
2024-06-18T17:02:28.767-04:00  INFO 4577 --- [dockerdemo] [           main] com.example.dockerdemo.Application       : Started Application in 1.682 seconds (process running for 2.166)
```

... then we will consider `2.166` seconds as the startup time.

### Extracted JAR

The simplest improvement we can make is to reduce the JAR extraction time. If the application is bundled in an OCI image, it is recommended to bundle an extracted JAR, not a self-executing JAR. The jar can be extracted by running [`bin/extract-jar.sh`](bin/extract-jar.sh). Notice that a new `dockerdemo-1.0` directory got created, see its contents.

Then run the application using the extracted JAR contents by invoking [`bin/run-extracted-jar.sh`](bin/run-extracted-jar.sh) and notice the startup time. This might be a very small improvement, around 10%.

### CDS Snapshot

The next improvement we can make is to create a CDS snapshot and use it to speed up class loading at starup. The CDS snapshot can be taken with [`bin/take-cds-snapshot.sh`](bin/take-cds-snapshot.sh). Notice that a new `application.jsa` file got created. This file contains the snapshot of all classes that need to be loaded on next startup.

Then run the application using the CDS snapshot by invoking [`bin/run-cds-jar.sh`](bin/run-cds-jar.sh) and notice the startup time. There might be upto 50% improvement compared to our base measurements.

### Native compile

The final improvement we can make is to compile our application as a native binary using GraalVM. First switch to a GraalVM installation and make sure that `java -version` shows that it is so. For example, the output from that command might be:

```
openjdk version "21.0.2" 2024-01-16
OpenJDK Runtime Environment GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30)
OpenJDK 64-Bit Server VM GraalVM CE 21.0.2+13.1 (build 21.0.2+13-jvmci-23.1-b30, mixed mode, sharing)
```

Then compile the application by running [`bin/make-native-binary.sh`](bin/make-native-binary.sh). This might take a minute or two. Way more than when we just compiled it before using `./mvnw clean package`. This is one of the downsides of compiling to native image. But there are benefits. First, notice the `target/dockerdemo` binary. This single binary is all that is needed to run your application on this and any other machine as long as it shares the same architecture and OS. The destination machine doesn't need any JRE/JDK installed. 

Let's start the native binary by invoking [`bin/run-native-binary.sh`](bin/run-native-binary.sh) and notice the startup time. On MacOS, there is a security related popup that wants your permission to run this new binary (it's a new binary that got created from the previous step and the OS is just making sure you know what you are doing). Notice the fast startup times. Usually, this can be an improvement between 10x to 20x.  
