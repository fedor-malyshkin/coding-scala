package playground.cats.effects

import cats.effect.unsafe.implicits.global
import cats.effect.{Async, IO, Resource}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.io.{File, FileInputStream}
import scala.language.higherKinds
import scala.math.BigInt

class CatsEffectsPlaygroundTest extends AnyFlatSpec {

  it should "check IO behaviour" in {
    val v = IO(2 + 2)
    v.unsafeRunSync() should be(4)
  }

  it should "check Resource behaviour" in {

    case class PseudoStream() {
      def open() = true

      def close(): Unit = ()
    }

    val res: Resource[IO, PseudoStream] = Resource.make {
      IO.blocking {
        val stream = PseudoStream()
        stream.open()
        stream
      }
    } {
      str =>
        IO.blocking(str.close()).handleErrorWith(th => IO.blocking {
          System.out.println(th.getMessage)
        })
    }
  }

  it should "check Async/Sync" in {
    import cats.effect.{Resource, Sync}
    def inputStream[F[_] : Sync](f: File): Resource[F, FileInputStream] = ???

    val t = Async[IO]

  }

}
