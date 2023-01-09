plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "de.climatelabs.ui"
  compileSdk = 33

  defaultConfig {
    minSdk = 26
    targetSdk = 32
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.3.2"
  }
}

dependencies {
  // in a real project we would have a smaller module just with the formatting logic which is what we need here
  implementation(project(":shared"))
  implementation("androidx.compose.ui:ui:1.2.1")
  implementation("androidx.compose.ui:ui-tooling:1.2.1")
  implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
  implementation("androidx.compose.foundation:foundation:1.2.1")
  implementation("androidx.compose.material:material:1.2.1")
}
