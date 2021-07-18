package playground.monix

import monix.eval.Task
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.{an, be}
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MonixPlaygroundTest extends AnyFlatSpec {

  {
    // https://monix.io/docs/3x/intro/hello-world.html

    // We need a scheduler whenever asynchronous
    // execution happens, substituting your ExecutionContext
    import monix.execution.Scheduler.Implicits.global

    // Needed below
    import scala.concurrent.duration._

    it should "check Task, the Lazy Future" in {
      import monix.eval._
      import monix.execution.CancelableFuture
      val task = Task {
        1 + 1
      }

      // Tasks get evaluated only on runToFuture!
      // Callback style:
      val cancelable = task.runAsync {
        case Right(value) =>
          println(value)
        case Left(ex) =>
          System.out.println(s"ERROR: ${ex.getMessage}")
      }
      //=> 2

      // Or you can convert it into a Future
      val future: CancelableFuture[Int] =
        task.runToFuture

      // Printing the result asynchronously
      future.foreach(println)
    }

    ignore should "check Observable, the Lazy & Async Iterable" in {
      import monix.reactive._

      // Nothing happens here, as observable is lazily
      // evaluated only when the subscription happens!
      val tick = {
        Observable
          .interval(1.second)
          // common filtering and mapping
          .filter(_ % 2 == 0)
          .map(_ * 2)
          // any respectable Scala type has flatMap, w00t!
          .flatMap(x => Observable.fromIterable(Seq(x, x)))
          // only take the first 5 elements, then stop
          .take(5)
          // to print the generated events to console
          .dump("Out")
      }
      // Execution happens here, after subscribe
      val cancelable = tick.subscribe()
      Thread.sleep(6000)
    }
  }

  {
    import monix.execution.atomic._
    it should "check Atomics" in {
      val refInt1: Atomic[Int] = Atomic(0)
      val refInt2: AtomicInt = Atomic(0)

      val refLong1: Atomic[Long] = Atomic(0L)
      val refLong2: AtomicLong = Atomic(0L)

      val refString1: Atomic[String] = Atomic("hello")
      val refString2: AtomicAny[String] = Atomic("hello")
    }

    it should "check Atomics Numbers" in {
      val ref = Atomic(BigInt(1))
      // now we can increment a BigInt
      ref.incrementAndGet() should be(BigInt("2"))
      // or adding to it another value
      ref.addAndGet(BigInt("329084291234234")) should be(BigInt("329084291234236"))
    }

    it should "check Atomics Number constrains" in {
      val string = Atomic("hello")
      assertDoesNotCompile("string.incrementAndGet()")
    }
  }

  {
    import monix.execution.Scheduler.Implicits.global
    it should "check Error Handling" in {
      import scala.util.{Failure, Success, Try}

      val source = Task.raiseError[Int](new IllegalStateException)
      // Now we can flatMap over both success and failure:
      val recoveredPre = source.flatMap(i => Task.now(i * 2))
      an[IllegalStateException] should be thrownBy (Await
        .result(recoveredPre.runToFuture, Duration.Inf))

      // val source = Task.raiseError[Int](new IllegalStateException)

      val materialized: Task[Try[Int]] =
        source.materialize

      // Now we can flatMap over both success and failure:
      val recovered = materialized.flatMap {
        case Success(value) => Task.now(value)
        case Failure(_)     => Task.now(0)
      }
      Await.result(recovered.runToFuture, Duration.Inf) should be(0)
    }

  }

}
