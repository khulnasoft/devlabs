plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.73.0")
    implementation("com.github.spotbugs:spotbugs-gradle-plugin:5.0.9")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.7")
}
