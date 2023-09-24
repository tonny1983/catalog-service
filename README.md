# Test

## Using both embedded and docker image DB for testing

### Problem to solve
Using embedded DB (`H2`) for develops in local developing and testing (without docker), 
meanwhile, a docker image (`testcontains`) is used in CI/CD pipeline.

### Solution
Using `H2` for default profile, while using `docker` profile for `testcontains`.

### Discussion
1. For `@SpringBootTest` tests, the datasources can be injected automatically in different environments,
however, it failed for `@DataJdbcTest` tests.
Therefore, for `@DataJdbcTest` tests, a generic test class `BookRepositoryTests` is created to list all test cases.
And then, two classes extend the generic class which inject required fields and initiate necessary resources (no need for `H2`).
2. It cannot set `spring.profiles.active` with gradle `test` task. 
As a solution, environment variables (`SPRING_PROFILES_ACTIVE=docker`) is used. 
However, for test classes, `@Profile` annotation not work due to [an issue](https://github.com/spring-projects/spring-framework/issues/16300).
When using gradle, the tests can be set to be ignored in `build.gradle` or `build.gradle.kts` files.

## Consumer Driven Contracts with Spring Cloud Contract

### Problem to solve
Publish stub jar to maven local repository with `Gradle`

### Solution
Add `verifierStubsJar` task in `publishing`

### Discussion
With `maven`, the stub jar can be installed to local repository by issuing 
```bash
mvn install
```
command.

The same thing in `gradle` is `maven-publish` plugin, and the detail can be found in [docs](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#publishing-your-application.maven-publish).

In order to publish stub jar, an artifact
```kotlin
artifact(tasks.named("verifierStubsJar"))
```
should be added just in the `create`.