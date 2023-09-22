import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun
import java.util.*

plugins {
    java
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.springdoc.openapi-gradle-plugin") version "1.7.0"
}

group = "cc.tonny"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    maven { url = uri("https://mirrors.huaweicloud.com/repository/maven/") }
    mavenCentral()

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
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

extra["springCloudVersion"] = "2022.0.4"

if (System.getenv("SPRING_PROFILES_ACTIVE") == "docker") {
    extra["testcontainersVersion"] = "1.19.0"
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    project.logger.info("my info message")
    useJUnitPlatform()

    if (System.getenv("SPRING_PROFILES_ACTIVE") == "docker") {
        println("HERE")
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

