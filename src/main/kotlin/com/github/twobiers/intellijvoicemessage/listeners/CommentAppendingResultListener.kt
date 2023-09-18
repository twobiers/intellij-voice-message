package com.github.twobiers.intellijvoicemessage.listeners

import com.github.twobiers.intellijvoicemessage.service.VoiceRecorderService
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import java.util.Base64

class CommentAppendingResultListener : VoiceRecordingResultListener {
  override fun onRecordingResult(inputStream: ByteArray) {
    // TODO
  }
}
