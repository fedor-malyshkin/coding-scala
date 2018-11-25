package cats

import cats.instances.list._

object CatsFunctors {
    def test01 = {
        val len: String => Int = _.length
        Functor[List].map(List("scala", "cats"))(len)
    }

    def test02 = {
        val r: Either[String, Int] = Right(100)
        r.map(x => x + 1)
    }

    def test03 = {
        import cats.instances.list._

        import scala.language.higherKinds
        // for Functor // for Functor
        val list1 = List(1, 2, 3)
        Functor[List].fproduct(list1)(_ * 2)
    }

    def test04 = {
        import cats.instances.list._

        import scala.language.higherKinds

        val f = (x: Int) => x * 100
        val fktor = Functor[List].lift(f)
        fktor.apply(List(1, 2, 3, 45))
    }

}
