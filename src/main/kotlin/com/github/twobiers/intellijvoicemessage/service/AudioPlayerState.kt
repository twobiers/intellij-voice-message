package com.github.twobiers.intellijvoicemessage.service

import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object AudioPlayerState {

  private val status = AtomicReference(Status.STOPPED)
  private val lock = ReentrantLock()

  enum class Status {
    STOPPED,
    PLAYING
  }

  private fun getStatus() = status.get()

  private fun setStatus(s: Status) {
    lock.withLock {
      status.set(s)
    }
  }

  val isPlaying get() = getStatus() == Status.PLAYING
  val isStopped get() = getStatus() == Status.STOPPED

  fun playing() = setStatus(Status.PLAYING)
  fun stopped() = setStatus(Status.STOPPED)
}
