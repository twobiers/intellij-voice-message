package com.github.twobiers.intellijvoicemessage.listeners

import com.github.twobiers.intellijvoicemessage.service.AudioPlayerService
import com.intellij.openapi.components.service

class PlayAudioResultListener : VoiceRecordingResultListener {
  private val audioPlayerService = service<AudioPlayerService>()

  override fun onRecordingResult(bytes: ByteArray) {
    audioPlayerService.play(bytes)
  }
}
