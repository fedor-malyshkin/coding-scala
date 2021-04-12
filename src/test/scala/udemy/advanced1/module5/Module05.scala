
/* Copyright (C) 2010-2017 Escalate Software, LLC. All rights reserved. */

package udemy.advanced1.module5

import org.scalatest.SeveredStackTraces
import org.scalatest.exceptions.TestFailedException
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.reflect.ClassTag
import scala.util.control.NonFatal

class Module05 extends AnyFunSuite with Matchers with SeveredStackTraces {

  // Look at, and understand, the Reversable[T] trait below that defines a contract
  // reverse(x: T): T that can be extended by other type classes

  trait Reversable[T] {
    def reverse(x: T): T
  }

  def reverse[T: Reversable](item: T): T = {
    implicitly[Reversable[T]].reverse(item)
  }

  // now, define an object ReversableString as an implementation of Reversable for String,
  // that simply calls reverse on the string.
  // Make it implicit
  implicit object ReversableString extends Reversable[String] {
    override def reverse(x: String): String = x.reverse
  }

  // uncomment the following test to make sure it works
  test("ReversableString reverses a given string") {
    reverse("hello") should be("olleh")
    reverse("") should be("")
  }

  // next define an object ReversableInt which does the same thing for a Reversable[Int], by reversing the digits
  // but still returning an Int, e.g. 1458 becomes 8541
  implicit object ReversableInt extends Reversable[Int] {
    override def reverse(x: Int): Int = Integer.parseInt(x.toString.reverse)
  }
  test("ReversableInt reverses the digits in an Int") {
    reverse(12345) should be(54321)
    reverse(1) should be(1)
    reverse(100) should be(1) // this is why this is a bit of a crap example :-)
  }


  // Write an implicit def to compose any T with reversable into a reverser for a List of T
  // that reverses both the contents of the List, and the List itself. Uncomment below to test.
  implicit def reversableList[R: Reversable]: Reversable[List[R]] = new Reversable[List[R]] {
    val reverser: Reversable[R] = implicitly[Reversable[R]]

    override def reverse(x: List[R]): List[R] = x
      .map(reverser.reverse)
      .reverse
  }


  test("Reverse a List of Ints") {
    reverse(List(123, 456, 100)) should be(List(1, 654, 321))
  }

  test("Reverse a List of Strings") {
    reverse(List("hello", "world")) should be(List("dlrow", "olleh"))
  }

  def interceptException[E: ClassTag](a: => Unit): Unit = {
    val ct = implicitly[ClassTag[E]]
    try {
      a
    } catch {
      case _: E => return
      case r => fail(s"Blimey! Wrong exception. Expected ${ct.runtimeClass} was ${r.getClass}")
    }
    fail("Blimey! No exception at all.")
  }

  test("Create your own intercept method") {
    // using an implicit class tag, create a new interceptException[T] method such that the following tests pass.

    // note that we use the name interceptException to avoid clashing with intercept which is a scalatest method
    // that does the same thing :-)
    // If the exception is thrown in the function, ignore it as expected, if it is not, fail with a message
    // that the exception was not thrown. If another exception than the one expected is thrown, fail with a
    // suitable method as well.

    // intercept a division by zero error
    interceptException[ArithmeticException] {
      val x = 1 / 0
    }

    // now let's expect an exception where there is none. Note we use our interceptException to test our
    // interceptException - how very meta :-)
    interceptException[TestFailedException] { // we should get a test failed exception here
      interceptException[ArithmeticException] { // this is the one that should not actually occur
        val x = 1 / 1   // well now, we shouldn't get an exception here should we?
      }
    }

    // and finally test that our interceptException detects the wrong kind of exception
    interceptException[TestFailedException] {
      interceptException[NumberFormatException] {
        val x = 1 / 0 // this is an ArithmeticException, not a NumberFormatException
      }
    }

  }

  test("Implicit parameter example") {

    // In the object below: Eventually, which has a couple of case classes: MaxTries and Interval.
    // update the eventually method (along with supporting method if necessary) which takes a by-name function,
    // and add implicit values for maxTries and interval. It should try the function up to MaxTries times
    // or until the function succeeds. If the function fails on a particular try, and throws an
    // exception (the expected failure mode), it should wait for the given interval and then try again
    // assuming the maxTries has not been reached.
    // Also add the implicit values for maxTries and interval with values of 10 and 100 respectively so that
    // they are picked up by the function and used as the defaults.
    // If maxTries is reached, re-throw the exception that was thrown by the function (i.e. we give
    // up after maxTries).
    // Check that the unit tests pass.
    object Eventually {

      case class MaxTries(value: Int)

      case class Interval(value: Int)

      implicit val maxTries: MaxTries = MaxTries(10)
      implicit val interval: Interval = Interval(100)


      def eventually(f: => Unit)(implicit maxTries: MaxTries, interval: Interval) {
        eventuallyWith(maxTries.value, interval.value)(f)
      }

      def eventuallyWith(maxTries: Int, interval: Int)(f: => Unit): Unit = {
        try {
          f
        } catch {
          case NonFatal(e) => if (maxTries > 0) {
            Thread.sleep(interval)
            eventuallyWith(maxTries - 1, interval)(f)
          } else
            throw e
        }
      }
    }


    import Eventually._

    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    val it = list.iterator

    eventually {
      it.next should be(6)
    }

    val it2 = list.iterator
    interceptException[TestFailedException] {
      eventually {
        it2.next should be(77)
      }
    }
  }


}
