package cats

import org.scalatest._


class CatsVarianceTest extends FlatSpec with Matchers {

    it should "test01" in {
        CatsVariance .test01 should be (List(5,4))
    }


}
