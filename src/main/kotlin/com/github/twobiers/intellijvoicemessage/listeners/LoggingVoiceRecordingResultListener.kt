package com.github.twobiers.intellijvoicemessage.listeners

import com.intellij.openapi.diagnostic.logger
import java.util.Base64

class LoggingVoiceRecordingResultListener : VoiceRecordingResultListener {
  private val logger = logger<LoggingVoiceRecordingResultListener>()

  override fun onRecording(recording: Boolean) {
    logger.debug("Recording: $recording")
  }

  override fun onRecordingResult(bytes: ByteArray) {
    val encoded = Base64.getEncoder().encodeToString(bytes)
    logger.debug("Recording Result: $encoded")
  }
}
