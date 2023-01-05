package de.climatelabs.kmmsample

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val formatter = DateTimeFormatter.ofPattern(Formatter.pattern, Locale.US)

actual val Instant.formatted
  get() = formatter.format(toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime())
