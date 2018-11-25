package cats

import org.scalatest._


class CatsMonoidTest extends FlatSpec with Matchers {

    it should "test01" in {
        CatsMonoid.test01 should be ("Hi there")
    }

    it should "test02" in {
        CatsMonoid.test02 should be ("")
    }

    it should "test03" in {
        CatsMonoid.test03 should be ("Hifrommonoids")
    }
}
