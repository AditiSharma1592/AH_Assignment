# Rijksmuseum API Tests

This project contains automated tests for the Rijksmuseum API using Serenity BDD, Cucumber, and Rest Assured.

## Tech

- **Java 17**
- **Maven**
- **Serenity BDD** - for test reporting and BDD support
- **Cucumber** - for BDD feature files
- **Rest Assured** - for API testing
- **JUnit 4** - for test execution

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   ├── runner/
│   │   │   └── TestRunner.java
│   │   └── stepDefinitions/
│   │       ├── BaseClass.java
│   │       ├── CollectionsSteps.java
│   │       └── ObjectDetailsSteps.java
│   └── resources/
│       ├── features/
│       │   ├── collections.feature
│       │   └── object_details.feature
│       └── cucumber.properties
```

## Run tests

```bash
mvn test
```

## Reports

After running tests, Serenity reports will be available at:
- `target/serenity-reports/`

## Features

- **Collections API**: Tests for retrieving collections from the Rijksmuseum API
- **Object Details API**: Tests for retrieving specific object details

