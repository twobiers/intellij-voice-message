package com.github.twobiers.intellijvoicemessage.listeners

import com.github.twobiers.intellijvoicemessage.service.VoiceRecorderService
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.logger
import java.util.Base64

class VoiceRecorderResultListener : VoiceRecordingResultListener {
  private val voiceRecorderService = service<VoiceRecorderService>()

  override fun onRecording(recording: Boolean) {
    if(recording) {
      voiceRecorderService.startRecording()
    } else {
      voiceRecorderService.stopRecording()
    }
  }
}
