package cats

import cats.CatsFunctors.Box
import org.scalatest._


class CatsFunctorsTest extends FlatSpec with Matchers {

    it should "test01" in {
        CatsFunctors.test01 should be(List(5, 4))
    }

    it should "test02" in {
        CatsFunctors.test02 should be(Right(101))
    }

    it should "test03" in {
        CatsFunctors.test03 should be(List((1, 2), (2, 4), (3, 6)))
    }

    it should "test04" in {
        CatsFunctors.test04 should be(List(100, 200, 300, 4500))
    }


    it should "testSelft" in {
        import cats.syntax.functor._
        import cats.instances.function._

        // for map
        val func1 = (a: Int) => a + 1
        val func2 = (a: Int) => a * 2
        val func3 = (a: Int) => a + "!"
        val func4 = func1.map(func2).map(func3)
        val func4a = func1 andThen func2 andThen  func3

        func4(1) should equal( func4a(1))
    }


    it should "test05" in {
        CatsFunctors.test05 should be(List(2, 3, 4, 5, 6))
    }

    it should "test06" in {
        CatsFunctors.test06 should be(Box(124))
    }

}
