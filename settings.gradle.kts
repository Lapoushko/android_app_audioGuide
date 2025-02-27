pluginManagement {
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

rootProject.name = "android_app_audioGuide"
include(":app")
include(":feature")
include(":feature:search")
include(":common")
include(":common:ui")
include(":feature:category")
include(":feature:detail-excursion")
include(":navigation")
include(":common:extension")
include(":feature:profile")
include(":feature:favourite")
include(":feature:guide")
include(":feature:guide:map")
include(":feature:guide:audio")
include(":common:util")
include(":feature:profile:setting")
include(":feature:profile:save")
include(":feature:profile:setting")
