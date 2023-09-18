package com.github.twobiers.intellijvoicemessage.service

import com.github.twobiers.intellijvoicemessage.listeners.VoiceRecordingResultListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.util.io.FileUtil
import com.intellij.util.messages.MessageBus
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import javax.sound.sampled.*


@Service
class VoiceRecorderService {
  private val log = logger<VoiceRecorderService>()
  private val messageBus: MessageBus by lazy { ApplicationManager.getApplication().messageBus }

  // For recording
  private lateinit var ais: AudioInputStream
  private lateinit var bao: ByteArrayOutputStream
  private lateinit var audioRecordingThread: Thread
  private val info = DataLine.Info(TargetDataLine::class.java, audioFormat)
  private val line: TargetDataLine = AudioSystem.getLine(info) as TargetDataLine

  companion object {
    val fileType: AudioFileFormat.Type = AudioFileFormat.Type.WAVE

    val audioFormat = wav()

    private fun wav(): AudioFormat {
      val sampleRate = 16000f
      val sampleSizeInBits = 8
      val channels = 2
      val signed = true
      val bigEndian = true
      return AudioFormat(
        sampleRate, sampleSizeInBits,
        channels, signed, bigEndian
      )
    }
  }

  init {
    if(!AudioSystem.isLineSupported(info)) {
      throw UnsupportedOperationException("Line not supported")
    }
  }

  fun startRecording() {
    if(!VoiceRecorderState.isReady) return

    VoiceRecorderState.recording()

    bao = ByteArrayOutputStream(16_000)
    audioRecordingThread = Thread {
      startActualAudioRecording(bao)
    }
  }

  fun stopRecording() {
    if(!VoiceRecorderState.isRecording) return

    stopActualAudioRecording()
    VoiceRecorderState.ready()
  }

  private fun startActualAudioRecording(bao: ByteArrayOutputStream) {
    line.open(audioFormat)
    line.start()

    val tmpFile = FileUtil.createTempFile("vc", ".wav")
    log.debug("Start recording...")
    ais = AudioInputStream(line)

    AudioSystem.write(ais, fileType, tmpFile)

    val fis = FileInputStream(tmpFile)
    bao.writeBytes(fis.readAllBytes())
    tmpFile.delete()
  }

  private fun stopActualAudioRecording() {
    log.debug("Stop recording...")
    line.stop()
    line.close()
    val bytes = bao.toByteArray()
    messageBus.syncPublisher(VoiceRecordingResultListener.RESULT_TOPIC).onRecordingResult(bytes)
  }
}
