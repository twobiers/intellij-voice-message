package com.github.twobiers.intellijvoicemessage.listeners

import com.intellij.util.messages.Topic

interface VoiceRecordingResultListener {
  companion object {
    @Topic.AppLevel
    val RESULT_TOPIC = Topic.create("Voice Recording Result", VoiceRecordingResultListener::class.java)
  }

  fun onRecording(recording: Boolean) {}

  fun onRecordingResult(bytes: ByteArray) {}
}
