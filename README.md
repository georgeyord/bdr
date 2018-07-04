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
