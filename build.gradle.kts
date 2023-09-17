import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
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
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<BootBuildImage>("bootBuildImage") {
    if (System.getProperty( "os.arch" ).lowercase(Locale.getDefault()).startsWith("aarch")) {
        builder = "ghcr.io/thomasvitale/java-builder-arm64"
    }
    builder = "paketobuildpacks/builder:tiny"
}