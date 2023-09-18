package com.github.twobiers.intellijvoicemessage.actions

import com.github.twobiers.intellijvoicemessage.listeners.VoiceRecordingResultListener
import com.github.twobiers.intellijvoicemessage.service.VoiceRecorderService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.service

class ToggleVoiceRecordingAction : AnAction() {
  private val messageBus = ApplicationManager.getApplication().messageBus
  private val voiceRecorderService = service<VoiceRecorderService>()

  @Volatile private var isRecording = false

  override fun actionPerformed(e: AnActionEvent) {
    if(isRecording) {
      isRecording = false
      voiceRecorderService.stopRecording()
    } else {
      isRecording = true
      voiceRecorderService.startRecording()
    }

    messageBus.syncPublisher(VoiceRecordingResultListener.RESULT_TOPIC).onRecording(isRecording)
  }
}
