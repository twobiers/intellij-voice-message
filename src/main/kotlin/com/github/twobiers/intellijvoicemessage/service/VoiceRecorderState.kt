package com.github.twobiers.intellijvoicemessage.service

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object VoiceRecorderState {

  private val status = AtomicReference(Status.READY)
  private val lock = ReentrantLock()

  enum class Status {
    READY,
    RECORDING
  }

  private fun getStatus() = status.get()

  private fun setStatus(s: Status) {
    lock.withLock {
      status.set(s)
    }
  }

  val isReady get() = getStatus() == Status.READY
  val isRecording get() = getStatus() == Status.RECORDING

  fun recording() = setStatus(Status.RECORDING)
  fun ready() = setStatus(Status.READY)
}
