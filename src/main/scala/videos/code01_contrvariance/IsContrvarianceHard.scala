package videos.code01_contrvariance

object IsContrvarianceHard {

  sealed class Animal

  class Dog(name: String) extends Animal // Dog <: (special symbol in Scala to mark the fact of extension) Animal

  def caseVariant(): Unit = {

    // if List[Dog] <: List[Animal] - they are COVARIANT
    val bobik = new Dog("Bobik")
    val someAnimal: Animal = bobik

    // in fact list is a List[+A]

    val dogs: List[Animal] = List(bobik, new Dog("Laika"), new Dog("Rex"))

  }

  def caseInvariant(): Unit = {
    // NOVARIANT CASE
    class MyInvariantType[T]

    // doesn't compile even
    // val myInvDogs : MyInvariantType[Animal] = new MyInvariantType[Dog]

  }


  def caseContrvariant(): Unit = {
    class MyContrvariantType[-T]

    val myContrvDogs: MyContrvariantType[Dog] = new MyContrvariantType[Animal]

    trait Vet[-T] {
      def heal(animal: T): Boolean
    }
    def callVet(): Vet[Animal] = new Vet[Animal] {
      override def heal(animal: Animal): Boolean = true
    }

    val mySickDog = new Dog("Sick Buddy")
    var vet: Vet[Dog] = callVet()
    vet.heal(mySickDog)
  }


  // CO-VARIANT - creates OR contains
  // CONTR-VARIANT  -- consumes OR processes

}
