package de.climatelabs.kmmsample

import kotlinx.datetime.Instant

internal object Formatter {
  const val pattern = "d MMM yyyy"
}

expect val Instant.formatted: String
