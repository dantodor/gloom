package lorcatech.gloom

/**
  * *********************************************************************
  * Copyright (c) 2017, Lorca Technologies                             *
  * Distributed under the MIT software license, see the accompanying   *
  * file COPYING or http://www.opensource.org/licenses/mit-license.php.*
  * *********************************************************************
  */

import org.scalatest.{FreeSpec, Matchers}

class UsageSpec extends FreeSpec with Matchers {
  "Test basic usage " in {
    val bloomFilter = BloomFilter(1000, 0.01)
    val bloomFilter2 = BloomFilter(1000, 0.01)

    bloomFilter2.add("")
    bloomFilter2.add("ThisIsMyString")

    bloomFilter.add("")
    bloomFilter.add("ThisIsMyString")

    bloomFilter.add("8f16c986824e40e7885a032ddd29a7d3")

    bloomFilter.contain("") shouldBe true
    bloomFilter.contain("ThisIsMyString") shouldBe true
    bloomFilter2.contain("") shouldBe true
    bloomFilter2.contain("ThisIsMyString") shouldBe true
    bloomFilter.contain("8f16c986824e40e7885a032ddd29a7d3") shouldBe true
    bloomFilter.contain("string") shouldBe false
    bloomFilter.makeEmpty
  }
}