package lorcatech.gloom

/**
  * *********************************************************************
  * Copyright (c) 2017, Lorca Technologies                             *
  * Distributed under the MIT software license, see the accompanying   *
  * file COPYING or http://www.opensource.org/licenses/mit-license.php.*
  * *********************************************************************
  */

import lorcatech.gloom.MemAlloc._

case class MutableBitArray(numBits: Long) extends Serializable {

  private val indices = math.ceil(numBits.toDouble / 64).toLong

  @transient
  private val ptr = unsafe.allocateMemory(8L * indices)
  unsafe.setMemory(ptr, 8L * indices, 0.toByte)

  def get(index: Long): Boolean = {
    (unsafe.getLong(ptr + (index >>> 6) * 8L) & (1L << index)) != 0
  }

  def set(index: Long): Unit = {
    val offset = ptr + (index >>> 6) * 8L
    val long = unsafe.getLong(offset)
    if ((long & (1L << index)) == 0) {
      unsafe.putLong(offset, long | (1L << index))
    }
  }

  def destroy: Unit = {
    unsafe.freeMemory(ptr)
  }
}
