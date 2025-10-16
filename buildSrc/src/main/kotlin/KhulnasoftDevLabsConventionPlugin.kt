package com.khulnasoft.devlabs

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class KhulnasoftDevLabsConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            // Apply common plugins
            pluginManager.apply("java")
            pluginManager.apply("idea")
            pluginManager.apply("com.diffplug.spotless")

            // Configure Java compatibility
            configureJava()

            // Configure Spotless for code formatting
            configureSpotless()

            // Configure repositories
            repositories {
                mavenCentral()
                gradlePluginPortal()
            }
        }
    }

    private fun Project.configureJava() {
        java {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }

    private fun Project.configureSpotless() {
        configure<SpotlessExtension> {
            java {
                googleJavaFormat()
                removeUnusedImports()
                trimTrailingWhitespace()
                endWithNewline()
            }
            kotlin {
                ktfmt()
                trimTrailingWhitespace()
                endWithNewline()
            }
            groovy {
                greclipse()
                trimTrailingWhitespace()
                endWithNewline()
            }
        }
    }
}

// Extension function to apply the convention plugin
fun Project.khulnasoftDevLabs() {
    pluginManager.apply("com.khulnasoft.devlabs.convention")
}
