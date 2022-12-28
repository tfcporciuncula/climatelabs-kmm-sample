plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  namespace = "de.climatelabs.kmmsample.android"
  compileSdk = 33
  defaultConfig {
    applicationId = "de.climatelabs.kmmsample.android"
    minSdk = 26
    targetSdk = 32
    versionCode = 1
    versionName = "1.0"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.3.2"
  }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
}

dependencies {
  implementation(project(":shared"))
  implementation("androidx.compose.ui:ui:1.2.1")
  implementation("androidx.compose.ui:ui-tooling:1.2.1")
  implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
  implementation("androidx.compose.foundation:foundation:1.2.1")
  implementation("androidx.compose.material:material:1.2.1")
  implementation("androidx.activity:activity-compose:1.5.1")
  implementation("androidx.navigation:navigation-compose:2.5.3")
  implementation("dev.marcellogalhardo:retained-activity:0.16.0")
  implementation("dev.marcellogalhardo:retained-navigation:0.16.0")
}
