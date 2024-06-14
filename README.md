# boot-docker-support-sample

A sample spring-boot app that demonstrates newly added [docker-compose support](https://docs.spring.io/spring-boot/reference/features/dev-services.html#features.dev-services.docker-compose) to create and configure service containers for local testing. It also shows usage of [test-containers](https://java.testcontainers.org/) for automated testing.

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