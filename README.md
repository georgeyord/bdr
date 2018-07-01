# bdr

### Prerequisites

- Java 8

### Clone project code

In your projects folder:

```
git clone https://github.com/georgeyord/bdr.git
```

### Import project to IDE

Import as Gradle project


### Build for the first time

To ensure dependencies are downloaded and tests are passing locally:

```
./gradlew clean build
```

### Run tests

To run unit tests:

```
./gradlew clean build test
```

To run integration (aka user acceptance or end to end) tests:
```
./gradlew integrationTest
```

### Run the application

```
./gradlew bootRun
```

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