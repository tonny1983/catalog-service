import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun
import java.util.*

plugins {
    java
    `maven-publish`
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springdoc.openapi-gradle-plugin") version "1.7.0"
    id("org.springframework.cloud.contract") version "4.0.4"
    kotlin("jvm") version "1.9.20-Beta2"
}

group = "cc.tonny"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}


repositories {
    mavenCentral()

}

buildscript {
    val scContractVersion = "4.0.4"
    dependencies {
        classpath("org.springframework.cloud:spring-cloud-contract-gradle-plugin:$scContractVersion")
        // remember to add this:
        classpath("org.springframework.cloud:spring-cloud-contract-spec-kotlin:$scContractVersion")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.2.0")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.flywaydb:flyway-core")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
    testImplementation(kotlin("stdlib-jdk8"))
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

extra["springCloudVersion"] = "2022.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    if ((System.getenv("SPRING_PROFILES_ACTIVE") ?: "".contains("docker")) == true) {
        systemProperty("spring.profiles.active", "docker")
        exclude("**/*Embedded*")
    } else {
        exclude("**/*Docker*")
    }
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    if (System.getProperty( "os.arch" ).lowercase(Locale.getDefault()).startsWith("aarch")) {
        builder.set("dashaun/builder:tiny")
    } else {
        builder.set("paketobuildpacks/builder:tiny")
    }

    imageName.set(project.name)
    environment.put("BP_JVM_VERSION", "17.*")

    if (System.getenv().containsKey("HTTP_PROXY")) {
        environment.put("HTTP_PROXY", System.getenv("HTTP_PROXY"))
    }
    if (System.getenv().containsKey("HTTPS_PROXY")) {
        environment.put("HTTPS_PROXY", System.getenv("HTTPS_PROXY"))
    }

}

tasks.getByName<BootRun>("bootRun") {
    systemProperty("spring.profiles.active", "testdata")
}

kotlin {
    jvmToolchain(17)
}


contracts {
    baseClassForTests.set("cc.tonny.catalogservice.contract.BaseTestsClass")
    contractsDslDir.set(project.file("${project.rootDir}/src/test/resources/contracts"))
}

publishing {
    publications {
        create<MavenPublication>("bootJava") {
            artifact(tasks.named("bootJar"))
            artifact(tasks.named("verifierStubsJar"))
        }
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}