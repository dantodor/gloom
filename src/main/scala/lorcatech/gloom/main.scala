package lorcatech.gloom


object Main extends App {
  override def main(args: Array[String]): Unit = {
    val expectedElements = 1000
    val falsePositiveRate: Double = 0.1
    val bf = BloomFilter(expectedElements, falsePositiveRate)
    bf.add("string").add("utxo")
    println(bf.contain("string"))
    println(bf.contain("strxxing"))
    println(bf.contain("utxo"))
    bf.makeEmpty
    println(bf.contain("utxo"))
  }
}