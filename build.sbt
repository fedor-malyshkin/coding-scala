scalaVersion := "2.12.6"

scalacOptions += "-Ypartial-unification"
scalacOptions ++= Seq("-deprecation")

lazy val coding = (project in file("."))
  .settings(
      name := "coding",
      libraryDependencies += "junit" % "junit" % "4.12" % Test,
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      libraryDependencies += "org.typelevel" %% "cats-core" % "1.5.0-RC1",
      libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0"
  )

// grading libraries
