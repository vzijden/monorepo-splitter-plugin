plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "1.7.10"
    id("idea")
    `maven-publish`
}

group = "nl.vzijden"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("RepoSplitter") {
            id = "nl.vzijden.repo.splitter"
            implementationClass = "nl.vzijden.repo.splitter.RepoSplitterPlugin"
        }
    }
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")

    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}