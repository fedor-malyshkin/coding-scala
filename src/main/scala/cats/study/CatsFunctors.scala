package cats.study

import cats.Functor
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


    def test05 = {
        import cats.syntax.functor._

        import scala.language.higherKinds
        // for map

        def doMath[F[_]](start: F[Int])(implicit functor: Functor[F]): F[Int] =
            start.map(n => n + 1)

        doMath(List(1, 2, 3, 4, 5))
    }

    final case class Box[A](value: A)

    def test06 = {
        import cats.syntax.functor._

        import scala.language.higherKinds
        // for map

        val box = Box[Int](123)

        implicit val boxFunctor =
            new Functor[Box] {
                override def map[A, B](fa: Box[A])(f: A => B): Box[B] = new Box[B](f(fa.value))
            }

        box.map(value => value + 1)
    }


}
