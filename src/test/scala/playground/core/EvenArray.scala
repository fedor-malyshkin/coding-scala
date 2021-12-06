package playground.core

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.collection.mutable

class EvenArray extends AnyFlatSpec {

  object Solution0 {
    /*
     * Complete the 'fetchItemsToDisplay' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_STRING_ARRAY items [[name, relevance, price],[name, relevance, price],[name, relevance, price]...]
     *  2. INTEGER sortParameter
     *  3. INTEGER sortOrder (0 - ASC, 1 - DSC)
     *  4. INTEGER itemsPerPage
     *  5. INTEGER pageNumber
     */
    def fetchItemsToDisplay(
      items: Array[Array[String]],
      sortParameter: Int,
      sortOrder: Int,
      itemsPerPage: Int,
      pageNumber: Int
    ): Array[String] = {
      println("items: " + items.mkString("Array(", ", ", ")"))
      println("items.length: " + items.length)
      println("sortParameter: " + sortParameter)
      println("sortOrder: " + sortOrder)
      println("itemsPerPage: " + itemsPerPage)
      println("pageNumber: " + pageNumber)

      object FieldOrdering extends Ordering[Array[String]] {
        def compare(a: Array[String], b: Array[String]) = if (sortOrder == 0)
          a(sortParameter) compare b(sortParameter)
        else b(sortParameter) compare a(sortParameter)
      }
      val realItemsSorted = items.sorted(FieldOrdering)

      val from = itemsPerPage * pageNumber
      val toPre = from + itemsPerPage
      val to = if (toPre > items.length) items.length else toPre
      realItemsSorted.slice(from, to).map(_(0))
    }

  }

  object Solution1 {

    def evenSubarrays(numbers: Array[Int], k: Int): Int = {
      val length = numbers.length
      var set = new mutable.HashSet[Int]()
      var count = 0
      for (start <- Array.range(0, length)) {
        for (end <- Array.range(start, length)) {
          val slice = numbers.slice(start, end + 1)
          if (slice.count(_ % 2 != 0) <= k) {
            val hash = slice.mkString(",").hashCode
            if (!set.contains(hash)) {
              set.add(hash)
              count = count + 1
            }
          }
        }
      }
      count
    }

  }
  object Solution2 {

    def countPairs(numbers: Array[Int], k: Int): Int = {
      println(k)
      println(numbers.mkString("Array(", ", ", ")"))
      val uniquesSortedNumbers = numbers.toSet.toArray.sorted
      val length = uniquesSortedNumbers.length
      if (k == 0) {
        length * 2
      } else {
        var result = 0
        for (start <- Array.range(0, length)) {
          for (end <- Array.range(start, length)) {
            val a = uniquesSortedNumbers(start)
            val b = uniquesSortedNumbers(end)
            if (a + k == b)
              result = result + 1
          }
        }
        result
      }
    }

  }
  it should "case 0" in {
    Solution0.fetchItemsToDisplay(
      Array(Array("p1", "1", "2"), Array("p2", "2", "1")),
      0,
      0,
      1,
      0
    ) should be(
      Array("p1")
    )

    Solution0.fetchItemsToDisplay(
      Array(
        Array("owjevtkuyv", "58584272", "62930912"),
        Array("rpaqgbjxik", "9425650", "96088250"),
        Array("dfbkasyqcn", "37469674", "46363902"),
        Array("vjrrisdfxe", "18666489", "88046739")
      ),
      2,
      1,
      2,
      0
    ) should be(
      Array("rpaqgbjxik", "vjrrisdfxe")
    )
    Solution0.fetchItemsToDisplay(
      Array(
        Array("owjevtkuyv", "58584272", "62930912"),
        Array("rpaqgbjxik", "9425650", "96088250"),
        Array("vjrrisdfxe", "18666489", "88046739")
      ),
      2,
      1,
      2,
      4
    ) should be(
      Array("owjevtkuyv")
    )
  }
  it should "case 1" in {
    Solution1.evenSubarrays(Array(1, 2, 3, 4), 1) should be(8)
    Solution1.evenSubarrays(Array(6, 3, 5, 8), 1) should be(6)
    Solution1.evenSubarrays(Array(2, 1, 2, 1, 3), 2) should be(10)

  }
  it should "case 2" in {
    Solution2.countPairs(Array(1, 1, 2, 2, 3, 3), 1) should be(2)
    Solution2.countPairs(Array(1, 2, 3, 4, 5, 6), 2) should be(4)
    Solution2.countPairs(Array(1, 2, 5, 6, 9, 10), 2) should be(0)

  }

}
