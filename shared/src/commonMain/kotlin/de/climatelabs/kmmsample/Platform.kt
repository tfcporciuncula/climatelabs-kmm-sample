package de.climatelabs.kmmsample

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform
