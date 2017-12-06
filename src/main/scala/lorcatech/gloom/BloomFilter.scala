package lorcatech.gloom

/**
  * *********************************************************************
  * Copyright (c) 2017, Lorca Technologies                             *
  * Distributed under the MIT software license, see the accompanying   *
  * file COPYING or http://www.opensource.org/licenses/mit-license.php.*
  * *********************************************************************
  */

import scala.annotation.tailrec
import scala.util.hashing.MurmurHash3

class BloomFilter(
  numBits: Long,
  numberOfHashes: Int,
  bitArray: MutableBitArray
) {

  def add(byt: String): BloomFilter = {
    val hash = murmurHash3(byt)
    val hash1 = hash >>> 32
    val hash2 = (hash << 32) >> 32

    0 to numberOfHashes map { i =>
      val computedHash = hash1 + i * hash2
      bitArray.set((computedHash & Long.MaxValue) % numBits)
    }
    this
  }

  def contain(byt: String): Boolean = {
    val hash = murmurHash3(byt)
    val hash1 = hash >>> 32
    val hash2 = (hash << 32) >> 32

    @tailrec
    def loop(i: Int): Boolean = {
      if(i < numberOfHashes) {
        val computedHash = hash1 + i * hash2
        if(!bitArray.get((computedHash & Long.MaxValue) % numBits)) {
          false
        } else loop(i + 1)
      } else true
    }
    loop(0)
  }

  def makeEmpty: BloomFilter = {
    bitArray.destroy
    new BloomFilter(numBits, numberOfHashes, bitArray)
  }

  def murmurHash3(str: String): Long = MurmurHash3.stringHash(str)
}

case object BloomFilter {

  def apply(numBits: Long, numberOfHashes: Int): BloomFilter = {
    new BloomFilter(numBits, numberOfHashes, MutableBitArray(numBits))
  }

  def apply(numBits: Long, falsePositiveRate: Double): BloomFilter = {
    val nb = optimalNumberOfBits(numBits, falsePositiveRate)
    val nh = optimalNumberOfHashes(numBits, nb)
    new BloomFilter(nb, nh, MutableBitArray(nb))
  }

  def optimalNumberOfBits(numberOfItems: Long, falsePositiveRate: Double): Long = {
    math.ceil(-1 * numberOfItems * math.log(falsePositiveRate) / math.log(2) / math.log(2)).toLong
  }

  def optimalNumberOfHashes(numberOfItems: Long, numberOfBits: Long): Int = {
    math.ceil(numberOfBits / numberOfItems * math.log(2)).toInt
  }
}
