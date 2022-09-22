dependencies {
    implementation(project(":module2"))

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

sourceSets {
    main {
        java {
            srcDir("main")
        }
    }
    test {
        java {
            srcDir("test")
        }
    }
}

