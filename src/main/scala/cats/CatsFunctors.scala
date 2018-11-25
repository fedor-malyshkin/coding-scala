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

}
