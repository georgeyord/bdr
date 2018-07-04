# bdr

### Prerequisites

- Java 8

> Or Docker. If you want to go with Docker read the section where it says you hate Java...

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


### Run the application locally BUT you hate Java ...

... and you want to run the application without installing Java locally, you can use Docker:

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

Use cURL to check the endpoint:
```
curl -X POST \
  http://localhost:8080/bid \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "e7fe51ce4f6376876353ff0961c2cb0d",
  "app": {
    "id": "e7fe51ce-4f63-7687-6353-ff0961c2cb0d",
    "name": "Morecast Weather"
  },
  "device": {
    "os": "Android",
    "geo": {
      "country": "USA",
      "lat": 0,
      "lon": 0
    }
  }
}'
```

### Next steps

As a very basic implementation this application, it misses core features along with reasonable extensions. I 'm listing here the things I would do in the next iterations.

- Logging
- Handling Exceptions with custom handler
- Security 