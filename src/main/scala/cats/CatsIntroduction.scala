package cats

object CatsIntroduction {

    trait Printable[A] {
        def format(value: A): String
    }


    object PrintableInstances {
        implicit val stringPrintable: Printable[String] = new Printable[String] {
            def format(input: String): String = input
        }
        implicit val intPrintable: Printable[Int] = new Printable[Int] {
            def format(input: Int): String = input.toString
        }
    }

    object PrintableInterfaceObject {
        def format[A](input: A)(implicit p: Printable[A]): String =
            p.format(input)

        def print[A](input: A)(implicit p: Printable[A]): Unit =
            println(format(input))
    }

    final case class Cat(name: String, age: Int, color: String)

    implicit val catPrintable: Printable[Cat] = new Printable[Cat] {
        def format(cat: Cat): String = {

            import PrintableInstances._

            val name = PrintableInterfaceObject.format(cat.name)
            val age = PrintableInterfaceObject.format(cat.age)
            val color = PrintableInterfaceObject.format(cat.color)
            s"$name is a $age year-old $color cat."
        }
    }


    object PrintableSyntax {

        implicit class PrintableOps[A](value: A) {
            def format()(implicit printable: Printable[A]): String = {
                printable.format(value)
            }

            def print()(implicit printable: Printable[A]): Unit = {
                println(printable.format(value))
            }
        }

    }

    def test01: String = {
        val cat = Cat("Garfield", 38, "ginger and black")
        PrintableInterfaceObject.format(cat)

    }

    def test02: String = {
        val cat = Cat("Garfield", 38, "ginger and black")

        import PrintableSyntax._
        cat.format()

    }

    def test03: String = {
        final case class Mat(name: String, age: Int, color: String)

        val mat = Mat("Garfield", 38, "ginger and black")

        import cats.implicits._


        implicit val matShow: Show[Mat] = new Show[Mat] {
            override def show(t: Mat): String =
                s"${t.name}"
        }
        mat.show

    }


}
