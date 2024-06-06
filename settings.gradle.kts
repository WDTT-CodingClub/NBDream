pluginManagement {
    includeBuild("build-src")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NBDream"

include(":app")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":core:domain")
include(":core:data")
include(":core:common")
include(":core:impl")
include(":core:ui")

include(":source:local")
include(":source:remote")
include(":source:oauth")

include(":feature:onboard")
include(":feature:main")
