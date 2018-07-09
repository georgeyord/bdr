### Features

- Supports withdrawals at `/withdraw` endpoint
- Reports current note status at `/status` endpoint
- Set the initial note count per type using `application.properties` during initialisation
- Supports $20 and $50 notes, easily extendable to any other note type
- Reports an error if amount is not available

### Prerequisites

- Java 8

> Or Docker. If you want to go with Docker read the section below

### Import project to IDE

Import as Gradle project and use Gradle wrapper

### Build for the first time

To ensure dependencies are downloaded and tests are passing locally:

```
./gradlew clean build
```

### Run tests

To run unit tests:

```
./gradlew clean test
```

To run integration (aka user acceptance or end to end) tests:
```
./gradlew clean integrationTest
```

### Run the application locally

```
./gradlew bootRun
```

### Run the application locally BUT you don't have Java ...

... and you want to run the application without installing it locally, you can use Docker:

```
docker run -it --rm \
    -p 8080:8080 \
    -v $(pwd):/opt/bdr \
    -v /tmp/bdr:/root/.gradle \
    -w=/opt/bdr \
    java:8 \
    ./gradlew bootRun
```

> Use the same command to run build/test etc by just changing the command found at the ending line

> The second time you run it it will not take long, I promise

### Use the application

Use cURL to check the `/withdraw` endpoint:
```
curl -X POST \
  http://localhost:8080/withdraw \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '290'
```

Use cURL to check the `/status` endpoint:
```
curl -X GET \
  http://localhost:8080/status \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json'
```