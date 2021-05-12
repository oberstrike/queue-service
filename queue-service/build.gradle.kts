plugins {
    kotlin("jvm") version "1.4.20"
    kotlin("plugin.allopen") version "1.4.20"
    kotlin("kapt") version "1.4.20"
    id("io.quarkus")
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://dl.bintray.com/oberstrike/maven")

}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")

    implementation("com.maju.proxy:proxy-generator:1.0.9")
    kapt("com.maju.proxy:proxy-generator:1.0.9")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.0")
    implementation("io.quarkus:quarkus-scheduler")

    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-keycloak-authorization")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-oidc")
    implementation("io.quarkus:quarkus-undertow-websockets")
    implementation("io.quarkus:quarkus-resteasy-jackson")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy")
    implementation("io.quarkus:quarkus-resteasy-mutiny")


    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-jdbc-h2")
    testImplementation("io.quarkus:quarkus-test-h2")
    testImplementation("io.quarkus:quarkus-keycloak-admin-client")
    testImplementation("io.quarkus:quarkus-test-security")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.quarkus:quarkus-junit5")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    testImplementation("com.maju.rest:rest:1.0.3")

    testImplementation("io.rest-assured:kotlin-extensions")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
    testImplementation("io.kotest:kotest-assertions-core:4.4.3")


    testImplementation("com.github.dasniko:testcontainers-keycloak:1.5.0")
    testImplementation("com.tngtech.keycloakmock:mock:0.7.0")
    testImplementation("org.testcontainers:testcontainers:1.15.1")
    testImplementation("io.quarkiverse.mockk:quarkus-junit5-mockk:0.2.0")

    val junitJupiterVersion = "5.7.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.testcontainers:junit-jupiter:1.15.1")


}

group = "de.ma"
version = "1.0.0-SNAPSHOT"

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("javax.persistence.Entity")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
