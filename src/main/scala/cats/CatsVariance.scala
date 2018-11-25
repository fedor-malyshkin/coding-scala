package cats

object CatsVariance {

    sealed trait Shape

    case class Circle(radius: Double) extends Shape


    def test01: Unit = {
        trait StringWriter[-A] {
            def writeString(value: A): String
        }

        val shape: Shape = new Shape {}
        val circle: Circle = new Circle(1)


        val shapeWriter: StringWriter[Shape] = new StringWriter[Shape] {
            override def writeString(value: Shape): String = s"shape: ${value}"
        }
        val circleWriter: StringWriter[Circle] = new StringWriter[Circle] {
            override def writeString(value: Circle): String = s"circle: ${value}"
        }

        def format[A](value: A, writer: StringWriter[A]): String = writer.writeString(value)

        format(shape, shapeWriter)
        format(circle, shapeWriter)
        format(circle, circleWriter)
    }


}
