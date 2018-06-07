scalaVersion := "2.12.6"

scalacOptions ++= Seq("-deprecation")

lazy val hello = (project in file("."))
  .settings(
      name := "coding",
      libraryDependencies += "junit" % "junit" % "4.12" % Test,
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )

// grading libraries
