pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "climatelabs-kmm-sample"
include(":androidApp")
include(":androidApp:ui")
include(":shared")
