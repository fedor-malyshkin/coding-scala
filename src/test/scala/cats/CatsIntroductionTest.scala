package cats

import org.scalatest._


class CatsIntroductionTest extends FlatSpec with Matchers {

    it should "test01" in {
        CatsIntroduction.test01 should be ("Garfield is a 38 year-old ginger and black cat.")
    }

    it should "test02" in {
        CatsIntroduction.test02 should be ("Garfield is a 38 year-old ginger and black cat.")
    }


    it should "test03" in {
        CatsIntroduction.test03 should be ("Garfield")
    }

}
