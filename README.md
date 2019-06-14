# TDD @ NCA

This project is intended to be used for showing how to develop a Couch DB driver in a test-driven fashion.

## gradle commands

This project uses [gradle](https://gradle.org/) as build tool.

To run the tests use

    ./gradlew test
    
To build a JAR of the lib (to be found in `build/libs`) use

    ./gradlew assemble
    
## Docker

For the tests, the Couch DB will by default be run in a docker container started by the test containers lib.
If you want to to use another Couch DB Instance, you can supply `useExternalCouchDB=true` as environment variable or 
system option, e.g. for command line

    env "useExternalCouchDB=true" ./gradlew test
    
or in the IDE for test execution:

    -DuseExternalCouchDB=true
    
If an external Couch DB is used it is assumed that the Couch DB is reachable at `http://localhost:5984`, 
you can change this through environment variable or system option `couchDbUrl` eg.

    -DcouchDbUrl=http://localhost:4711

## Technologies

* [Groovy Language](http://www.groovy-lang.org/)
* [Gradle Build Tool](https://gradle.org/)
* [Spock Testing Framework](http://spockframework.org/)
* [httpbuilder](https://github.com/jgritman/httpbuilder) - groovy http client
* [testcontainers](https://github.com/testcontainers/testcontainers-java) - docker container handling for automated tests
* [Jackson JSON Library](https://github.com/FasterXML/jackson)
    * [Jackson datatype JSONObject](https://github.com/FasterXML/jackson-datatype-json-org)