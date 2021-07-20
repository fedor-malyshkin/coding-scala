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

}
