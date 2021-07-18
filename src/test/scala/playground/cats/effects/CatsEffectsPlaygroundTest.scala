package playground.cats.effects

import cats.effect.{Async, IO, Resource}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.io.{File, FileInputStream}
import scala.language.higherKinds

class CatsEffectsPlaygroundTest extends AnyFlatSpec {

  it should "check IO behaviour" in {
    val v = IO(2 + 2)
    v.unsafeRunSync() should be(4)
  }

  it should "check Async/Sync" in {
    import cats.effect.{Resource, Sync}
    def inputStream[F[_]: Sync](f: File): Resource[F, FileInputStream] = ???

    val t = Async[IO]

  }

}
