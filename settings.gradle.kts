pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "khulnasoft-devlabs"

// Include all IntelliJ projects
include("intellij-projects:ai-spock-groovy-combo")
include("intellij-projects:java-spring-refactor")
include("intellij-projects:functional-test-rest-service")
include("intellij-projects:smart-register")
include("intellij-projects:pr-insight")
include("intellij-projects:selenium-tests")
include("intellij-projects:coverage-ai")
include("intellij-projects:tdd:price-calculator")

// Include build tool projects
include("build-tool-projects:gradle")
include("build-tool-projects:maven")
include("build-tool-projects:webpack")

// Include ad-hoc projects
include("ad-hoc-projects:picocli-main")

project(":intellij-projects:tdd:price-calculator").projectDir = file("intellij-projects/tdd/price-calculator")
