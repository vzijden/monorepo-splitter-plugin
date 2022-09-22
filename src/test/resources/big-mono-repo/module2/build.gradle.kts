dependencies {
    implementation(project(":module3"))
    implementation(project(":module4"))

    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

