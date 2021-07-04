package playground.cats.effects

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers.be
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class CatsEffectsPlaygroundTest extends AnyFlatSpec {

  it should "check IO behaviour" in {
    val v = IO(2 + 2)
    v.unsafeRunSync() should be(4)
  }


}
