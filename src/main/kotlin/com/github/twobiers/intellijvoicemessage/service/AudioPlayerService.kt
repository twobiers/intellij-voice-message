package com.github.twobiers.intellijvoicemessage.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.debug
import com.intellij.openapi.diagnostic.logger
import java.io.ByteArrayInputStream
import javax.sound.sampled.*


@Service
class AudioPlayerService : LineListener {
  private val log = logger<AudioPlayerService>()
  companion object {
    val audioFormat = VoiceRecorderService.audioFormat
  }

  // For playback
  private lateinit var clip: Clip
  val info = DataLine.Info(Clip::class.java, audioFormat)

  fun play(bytes: ByteArray) {
    if(!AudioPlayerState.isPlaying) return

    AudioPlayerState.playing()
    playActual(bytes)
  }

  fun stop() {
    if(!AudioPlayerState.isStopped) return

    stopActual()
    AudioPlayerState.stopped()
  }

  private fun playActual(bytes: ByteArray) {
    val ais = AudioSystem.getAudioInputStream(ByteArrayInputStream(bytes))
    clip = AudioSystem.getLine(info) as Clip
    clip.addLineListener(this)
    clip.open(ais)
    clip.start()
  }

  private fun stopActual() {
    clip.stop()
  }

  override fun update(event: LineEvent) {
    val type = event.type

    if (type === LineEvent.Type.START) {
      log.debug("Playback started.")
    } else if (type === LineEvent.Type.STOP) {
      log.debug("Playback completed.")
      AudioPlayerState.stopped()
    }
  }
}
