package de.climatelabs.kmmsample

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toNSDate
import kotlinx.datetime.toNSTimeZone
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale

private val formatter = NSDateFormatter().apply {
  dateFormat = Formatter.pattern
  locale = NSLocale(localeIdentifier = "en_US")
}

actual val Instant.formatted: String
  get() {
    return formatter.apply { timeZone = TimeZone.currentSystemDefault().toNSTimeZone() }.stringFromDate(toNSDate())
  }
