package cats

object CatsMonoid {


    def test01 = {
        import cats.instances.string._ // for Monoid
        Monoid[String].combine("Hi ", "there")
    }

    def test02 = {
        import cats.instances.string._ // for Monoid
        Monoid[String].empty
    }

    def test03 = {
        import cats.instances.string._ // for Monoid
        import cats.syntax.semigroup._ // for Monoid
        "Hi" |+| "from" |+| "monoids"
    }
}