package cats

object CatsMonads {
    def test01 = {
        List(List(1, 2, 3), List(3, 4, 5)).flatMap(a => a)
    }


    def test02 = {
        for {
            x <- (1 to 3).toList
            y <- (4 to 5).toList
        } yield (x, y)
    }

    case class Cat(name: String, favoriteFood: String)

    def testReaderMonad01 = {
        import cats.data.Reader
        // defined class Cat
        val catName: Reader[Cat, String] = Reader(cat => cat.name)
        catName
    }

    def testReaderMonad02 = {
        import cats.data.Reader
        // defined class Cat
        val catName: Reader[Cat, String] = Reader(cat => cat.name)
        catName.run(Cat("Garf", "lasagne"))
    }


    def testReaderMonad03 = {
        import cats.data.Reader
        // defined class Cat
        val catName: Reader[Cat, String] = Reader(cat => cat.name)

        val greetCat = catName.map(name => s"Hello, $name")
        greetCat.run(Cat("Garf", "lasagne"))
    }


    def testReaderMonad04 = {
        import cats.data.Reader
        // defined class Cat
        val catName: Reader[Cat, String] = Reader(cat => cat.name)
        val greetKitty = catName.map(name => s"Hello, $name")
        val feedKitty: Reader[Cat, String] =
            Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")
        val greetAndFeed: Reader[Cat, String] =
            for {
                greet <- greetKitty
                feed
                  <- feedKitty
            } yield s"$greet. $feed."
        greetAndFeed(Cat("Garfield", "lasagne"))
    }

}
