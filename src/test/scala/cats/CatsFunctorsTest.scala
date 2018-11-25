package cats

import org.scalatest._


class CatsFunctorsTest extends FlatSpec with Matchers {

    it should "test01" in {
        CatsFunctors.test01 should be (List(5,4))
    }

    it should "test02" in {
        CatsFunctors.test02 should be (Right(101))
    }

}
