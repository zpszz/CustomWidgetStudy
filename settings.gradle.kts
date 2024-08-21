pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/google")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/google")
        google()
        mavenCentral()
    }
}

rootProject.name = "CustomWidgetStudy"
include(":app")
include(":chapter2")
include(":chapter3")
include(":chapter5")
include(":chapter12")
include(":chapter13")
include(":chapter14")
include(":chapter15")
include(":chapter16")
