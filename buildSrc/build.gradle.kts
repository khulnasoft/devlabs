plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.51.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.4")
}
